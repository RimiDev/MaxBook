/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ehugh
 */
@Named
@SessionScoped
public class CartBackingBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CartBackingBean.class.getName());

    @Inject
    private BookJpaController bookJpaController;


    public void add_to_cart(String isbn) {
        logger.log(Level.WARNING,"inside CartBackinBean  add_to_cart isbn param " + isbn);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        List<Book> cart = new ArrayList<>();
        Book book = bookJpaController.findBook(isbn);
        cart.add(book);
        logger.log(Level.WARNING, ">>>>>> add_to_cart() method book: " + book);
        session.setAttribute("cartItems", cart);
        cart = (List<Book>) session.getAttribute("cartItems");
        logger.log(Level.WARNING, ">>>>>> add_to_cart() method cartsize after add: " + cart.size());

    }
}
