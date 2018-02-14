package com.rimidev.maxbook.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class BookDisplayBacking {

    public String showDetails(String isbn){
        
        if(isbn.isEmpty() || isbn == null){
            
        }
        return "/BookDetails.xhtml?faces-redirect=truse&isbn="+isbn; 
    }
}
