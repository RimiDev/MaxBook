package com.rimidev.maxbook.beans;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Book;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class BookDisplayBacking implements Serializable{

    @Inject
    private BookJpaController bookjpaControl;

    public String showDetails(String isbn){
        
        if(isbn.isEmpty() || isbn == null){
            return "/404.xhtml"; 
        }
        
        Book bookIsExist = bookjpaControl.findBook(isbn);
        if(bookIsExist == null)
            return "/404.xhtml";
        
        return "/BookDetails.xhtml?faces-redirect=truse&isbn="+isbn; 
    }
}
