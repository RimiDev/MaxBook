/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.NewsJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.News;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 *
 * @author maximelacasse
 */
@Named
@RequestScoped
public class HomeBackingBean {

    @Inject
    private InvoiceDetailsJpaController invoiceDetails;
    @Inject
    private NewsJpaController news;
    @Inject
    private BookJpaController booksJpa;

    private List<Book> topSellingBooks;

    private List<Book> recentSoldBooks;
    
    private List<Book> clientTrackedBooks;
    
        private static final Logger LOG = Logger.getLogger("PreRenderViewBean.class");


    public List<Book> getTopSellingBooks() {
        if (topSellingBooks == null) {
            topSellingBooks = invoiceDetails.getTopSellingBooks();
        }
        return topSellingBooks;
    }

    public List<Book> getRecentSoldBooks() {
        if (recentSoldBooks == null) {
            recentSoldBooks = invoiceDetails.getRecentSoldBook();
        }
        return recentSoldBooks;
    }

    public String getActiveNews() {
        List<News> ns = news.findNewsEntities().stream()
        .filter(i -> i.getActiveStatus() == true).collect(Collectors.toList());
        
        return ns.get(0).getReaderLink();
    }
    
    public List<Book> getClientTrackedBooks(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
         Object my_cookie = context.getExternalContext().getRequestCookieMap().get("recentGenre");
        if (my_cookie != null) {
            LOG.info(((Cookie) my_cookie).getName());
            LOG.info(((Cookie) my_cookie).getValue());
            
                    if (clientTrackedBooks == null){
            clientTrackedBooks = booksJpa.getBookByGenre(((Cookie) my_cookie).getValue());
        }
        return clientTrackedBooks;
            
            
            
        }
        return null;
    }
}
        
        
        
        

