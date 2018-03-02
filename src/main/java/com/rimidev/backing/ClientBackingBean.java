/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.util.MessagesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maximelacasse
 */
@Named
@RequestScoped
public class ClientBackingBean implements Serializable {

    private static final Logger logger = Logger.getLogger(ClientBackingBean.class.getName());
    
    @Inject
    private ClientJpaController clientJpaController;

    private Client client;
    
    //private LoginBackingBean loginBean;

    /**
     * Client created if it does not exist.
     *
     * @return
     */
    public Client getClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    /**
     * Save the current person to the db
     *
     * @return
     * @throws Exception
     */
    public String createClient() throws Exception {
        if (isValidEmail()){
        clientJpaController.create(client);
        return "home";
        } else {
            return null;
        }
    }
    
    public String onLogin(String username){
        logger.log(Level.WARNING,"inside ClientBackingBean onLogin" + username);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client client = clientJpaController.findClientByEmail(username);
        logger.log(Level.INFO, "(Login)User >>> "+client);
        session.setAttribute("current_user", client );
        session.setAttribute("cartItems", new ArrayList<Book>());
        return "home";

    }
    
    
    /**
     * This method checks to see if the email address is already exists.
     *
     * @return A boolean value.
     */
    private boolean isValidEmail() {
        boolean valid = false;
        String email = client.getEmail();
        if (email != null) {
            if (clientJpaController.findClientByEmail(email) == null) {
                valid = true;
            } else {
                FacesMessage message = MessagesUtil.getMessage(
                        "bundles.messages", "email.in.use", null);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("signupForm:email", message);
            }
        }
        return valid;
    }

    
 


}
