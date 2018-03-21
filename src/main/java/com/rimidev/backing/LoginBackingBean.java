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
  

  private static final Logger logger = Logger.getLogger(LoginBackingBean.class.getName());
  
  @Inject
  private ClientJpaController clientJpaController;
  private Client client;
  private String styling;
  private String passwordsDontMatch;
  private String comparePassword;

  public void setPasswordsDontMatch(String passwordsDontMatch) {
    this.passwordsDontMatch = passwordsDontMatch;
  }
  
  public String checkPassword(String password){
    if (password.length() == 0){
      return "  invalid password length";
    }else{
      return "";
    }
  }

  public String getInvalidPasswordMessage() {
    return "  invalid password";
  }
  
  

  public String createClient() throws Exception {

    logger.log(Level.INFO, "inside on createClient");
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    Client current_user = clientJpaController.findClientByEmail(client.getEmail());

    if (current_user == null) {
      logger.log(Level.INFO, "inside createClient >>> " + client.getEmail());
      logger.log(Level.INFO, "inside createClient password" + client.getPassword());
      if (client.getPassword().equals(comparePassword)) {
        clientJpaController.create(client);
        session.setAttribute("current_user", client);
        session.setAttribute("cartItems", new ArrayList<Book>());

        return "home?faces-redirect=true";
      }else{
        
      }
    }

    return null;

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
  
  public void validateMatchingPasswords(FacesContext fc, UIComponent c, Object value) {

    comparePassword = (String) value;
    if (!client.getPassword().equals(comparePassword)) {
      throw new ValidatorException(new FacesMessage(
              "passwords don't match"));
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

  public String onLogin() {

    logger.log(Level.INFO, "inside on login");
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    Client registered_user = clientJpaController.findClientByEmail(client.getEmail());

    if (registered_user != null) {
      if (registered_user.getPassword().equals(client.getPassword())) {

        session.setAttribute("current_user", registered_user);
        
        if (session.getAttribute("cartItems") == null){
        
            session.setAttribute("cartItems", new ArrayList<Book>());
        }

        return "home?faces-redirect=true";
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
    } else {

    return "login?faces-redirect=true";

    }
  }

  public String onSignUp() {

    logger.log(Level.INFO, "onSignUp >>> ");
    return "register";

  }
  
  public String getComparePassword() {
    return comparePassword;
  }

  public void setComparePassword(String compare_password) {
    this.comparePassword = compare_password;
  }

  public Client getClient() {
    if (client == null) {
      client = new Client();
    }
    return client;
  }

}
