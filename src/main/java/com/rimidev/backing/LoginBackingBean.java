/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maximelacasse
 */
@Named("loginBackingBean")
@SessionScoped
public class LoginBackingBean implements Serializable {

  

  private static final Logger logger = Logger.getLogger(ClientBackingBean.class.getName());
  
  @Inject
  private ClientJpaController clientJpaController;
  private Client client;
  private String styling;




  public String getInvalidPasswordMessage() {
    return "  invalid password";
  }


  public String createClient() throws Exception {

    clientJpaController.create(client);
    return "home";

  }

   public Client getClient() {
    if (client == null) {
      client = new Client();
    }
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void validateEmail(FacesContext fc, UIComponent c, Object value) {

    String email = (String) value;
    String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // Create a Pattern object
    Pattern r = Pattern.compile(pattern);

    // Now create matcher object.
    Matcher m = r.matcher(email);
    if (!m.find()) {
      throw new ValidatorException(new FacesMessage(
              "Email improperly typed"));
    }

  }

  public String getStyling() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    Client curr_user = (Client) session.getAttribute("current_user");

    if (curr_user == null) {
      return "Login";
    }
    return "Logout";
  }

  public void validatePassword(FacesContext fc, UIComponent c, Object value) {

    String password = (String) value;

  }

  public String doSomeAction() {

    if (true) {
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message Displayed Growl",
                      "Error Message Displayed Growl"));
    }

    return null;

  }

  public String onLogin() {

    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    Client registered_user = clientJpaController.findClientByEmail(client.getEmail());

    if (registered_user != null) {
      logger.log(Level.INFO, "onLogin registered user email is >>> " + registered_user.getEmail());
      logger.log(Level.INFO, "inside ClientBackingBean onLogin" + registered_user.getEmail());
      if (registered_user.getPassword().equals(client.getPassword())) {

        session.setAttribute("current_user", registered_user);
        session.setAttribute("cartItems", new ArrayList<Book>());

        return "home";
      }
    }

    return null;

  }

  public String onLogout() {

    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    Client curr_user = (Client) session.getAttribute("current_user");

    // log out user if they exist
    if (curr_user != null) {
      session.setAttribute("current_user", null);
      curr_user = null;

      return null;
    }

    return "login";

  }

}
