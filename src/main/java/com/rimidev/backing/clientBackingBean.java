/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.entities.Client;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author maximelacasse
 */
@Named
@RequestScoped
public class clientBackingBean implements Serializable {

    @Inject
    private ClientJpaController clientJpaController;

    private Client client;

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
        clientJpaController.create(client);
        return null;
    }
}
