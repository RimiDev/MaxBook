package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author eric
 */



@Named("accountDetailsBacking")
@SessionScoped
public class AccountDetailsBacking implements Serializable{
  private static final Logger logger = Logger.getLogger(AccountDetailsBacking.class.getName());
  
  @Inject
  private ClientJpaController clientJpaController;
  
  
  public void checkAddresses(FacesContext fc, UIComponent c, Object value) {
    String address = (String) value;
    
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");

    String message = bundle.getString("addressRequired");
    if (address.length() == 0) {
      throw new ValidatorException(new FacesMessage(
              message));
    }

  }
  
   public void checkNames(FacesContext fc, UIComponent c, Object value) {
    String name = (String) value;
    
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");

    String message = bundle.getString("firstNameRequired");
    if (name.length() == 0) {
      throw new ValidatorException(new FacesMessage(
              message));
    }

  }
  
  
  
  public void checkPostalCode(FacesContext fc, UIComponent c, Object value) {
    String postalcode = (String) value;
    String pattern = "^[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]$";

    // Create a Pattern object
    Pattern r = Pattern.compile(pattern);

    // Now create matcher object.
    Matcher m = r.matcher(postalcode);
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");

    String message = bundle.getString("improperPostalCode");
    if (!m.find()) {
      throw new ValidatorException(new FacesMessage(
              message));
    }

  }
}
