package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Author;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class BookDisplayBacking implements Serializable {

    private Logger logger = Logger.getLogger(BookDisplayBacking.class.getName());

    @Inject
    private BookJpaController bookjpaControl;
    private String isbn = "";
    private String visibilityStyle="";

    public String getVisibilityStyle() {
        return visibilityStyle;
    }

    public void setVisibilityStyle(String visibiltyStyle) {
        this.visibilityStyle = visibiltyStyle;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String showDetails() {
        
        hideReview();

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        isbn = params.get("isbn");
        
        logger.log(Level.INFO, "Book Isbn>>> " + this.isbn);

        return "bookDetails?faces-redirect=true";
    }
    
    public void checkBookExist(ComponentSystemEvent event){
         //need to fix reidrect where i cant go directly to book details page
        logger.log(Level.INFO, "We in checkBookExist");
        logger.log(Level.INFO, "isbn " + this.isbn);
         if(isbn == null || isbn.isEmpty()){
             logger.log(Level.INFO, "isbn " + this.isbn);
        
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            handler.performNavigation("404");
        }
         
    }
    

    public List<Book> getRecsByAuthor(List<Author> auths) {
        
        List<Integer> authIds = new ArrayList<Integer>();
        for (Author au : auths) {
            authIds.add(au.getId());
        }
        
        List<Book> authBooks = bookjpaControl.getBooksByAuthor(authIds, isbn);
        
        return authBooks;
    }

    public Double checkSales(int salePrice) {
        return 0.0;
    }
    
    public void hideReview(){
          HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client curr_user = (Client) session.getAttribute("current_user");
        
        logger.log(Level.INFO,"Currently logged in: "+curr_user);
        
        if(curr_user == null){
           visibilityStyle = "hideRevStyle";
        } else {
            visibilityStyle = "";
        }
        
    }
}
