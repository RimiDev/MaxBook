package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Author;
import com.rimidev.maxbook.entities.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
    private String isbn = "1";

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String showDetails() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        isbn = params.get("isbn");
        logger.log(Level.INFO, "Book Isbn>>> " + this.isbn);
        
        if(isbn == null || isbn.isEmpty()){
            return "404";
        }
       
        
        return "bookDetails?faces-redirect=true";
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
}
