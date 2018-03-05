package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Book;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Philippe Langlois-Pedroso, 1542705
 */
@Named
@RequestScoped
public class DownloadsBackingBean implements Serializable{
    
    @Inject
    private BookJpaController bookJpaController;

    private static final Logger logger = Logger.getLogger(DownloadsBackingBean.class.getName());
    
    private List<Book> cart;
    
    public List<Book> getBooks(){
        logger.info("DownloadsBackingBean -> getBooks()");   
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
        if(session.getAttribute("cartItems") == null){
            logger.info("cart Items null");
        } else{
            cart = bookJpaController.findBookEntities(2, 4);
        }
        return cart;
    }
    
    public void getDownload(){
        logger.info("Downloading Book");
    }
}
