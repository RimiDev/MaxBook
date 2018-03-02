/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author maximelacasse
 */
@Named("loginBackingBean")
@SessionScoped
public class LoginBackingBean implements Serializable {

  private String email;
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
  
  public void validateEmail(FacesContext fc, UIComponent c, Object value) {
    
     String email = (String)value;
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

  public void validatePassword(FacesContext fc, UIComponent c, Object value) {
    
     String password = (String)value;
      

    
  }



  public String doSomeAction() {

    if (true) {
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message Displayed Growl",
                      "Error Message Displayed Growl"));
    }

    return null;

  }

  
}


