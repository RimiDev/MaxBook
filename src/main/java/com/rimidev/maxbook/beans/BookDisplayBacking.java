package com.rimidev.maxbook.beans;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Book;
import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rhai Hinds
 */
@Named()
@SessionScoped
public class BookDisplayBacking implements Serializable{

    @Inject
    private BookJpaController bookjpaControl;
    String data = "1";
    
    public String getData(){
        return data;
    }
    
    public void setData(String data){
        this.data = data;
    }
    public String showDetails(){
        

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        data = params.get("isbn");
        return "bookDetails?faces-redirect=true"; 
    }
}
