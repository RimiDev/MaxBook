/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.beans;

import com.rimidev.maxbook.controller.PublisherJpaController;
import com.rimidev.maxbook.entities.Publisher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rhai
 */

@Named
@SessionScoped
public class PublishersBean implements Serializable{
    
    @Inject
    PublisherJpaController publisherController;
    
    private Publisher selectedPub;
    private List<Publisher> pubs;

    @PostConstruct
    public void init() {
        this.pubs = publisherController.findPublisherEntities();
        
    }

    public Publisher getSelectedPub() {
        return selectedPub;
    }

    public void setSelectedPub(Publisher selectedPub) {
        this.selectedPub = selectedPub;
    }

    public List<Publisher> getPubs() {
        return pubs;
    }

    public void setPubs(List<Publisher> pubs) {
        this.pubs = pubs;
    }
    
    public Publisher getPub(Integer id){
        if(id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for(Publisher pub : pubs){
            if(id.equals(pub.getId())){
                return pub;
            }
        }
        return null;
    }
    
    
    
    
}
