/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ehugh
 * @author maxime Lacasse
 */
@Named
@SessionScoped
public class CartBackingBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CartBackingBean.class.getName());

    private List<Book> cart;
    @Inject
    private BookJpaController bookJpaController;
    @Inject
    private InvoiceJpaController invoiceJpaController;


    public void addToCart(String isbn) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
               
        cart = (List<Book>) session.getAttribute("cartItems");
        Book book = bookJpaController.findBook(isbn);
        Boolean bookAlreadyInCart = false;
        
        for (int i = 0; i < cart.size(); i++){
            if (cart.get(i).getIsbn().equals(book.getIsbn())){
                bookAlreadyInCart = true;
                break;
            }
        }
        
        if (!bookAlreadyInCart){
            cart.add(book);
            session.setAttribute("cartItems", cart);
        }
        
          Client curr_user = (Client) session.getAttribute("current_user");

    }
    
     public String removeFromCart(String isbn) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        cart = (List<Book>) session.getAttribute("cartItems");
        
        
         for (Iterator<Book> iterator = cart.iterator(); iterator.hasNext();) {
             Book next = iterator.next();
             if (next.getIsbn().equals(isbn)){
                 cart.remove(next);
             break;
             }
         }
         session.setAttribute("cartItems", cart);
         
         return null;
       
    }
     
     
     public double generateTotal(){
         
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);         
        cart = (List<Book>) session.getAttribute("cartItems");
        
        double total = 0.0;
        
        for (int i = 0; i < cart.size(); i++){
            total += cart.get(i).getSalePrice().doubleValue();
        }
        
        return Double.valueOf(String.format("%.2f", total));
        
     }
     
     public Double generateTaxTotal(){
         
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);         
        Client user = (Client) session.getAttribute("current_user");
        double tax = user.getProvince().getHSTrate().doubleValue();
        
        double taxTotal = (generateTotal() * tax);
        return Double.valueOf(String.format("%.2f", taxTotal));
         
     }
     
     public Double generateTotalTaxedSale(){
         
         
         return Double.valueOf(String.format("%.2f", (generateTotal() + generateTaxTotal())));
     }
     
     public String validateCreditCardInformation(Client id, Timestamp dateSale, BigDecimal net, BigDecimal gross) throws Exception{
         
         
         //insert invoice to database
         Invoice i = new Invoice();
         
         i.setClientId(id);
         i.setDateOfSale(new Date());
         i.setGrossValue(gross);
         i.setNetValue(net);
         
         invoiceJpaController.create(i);
       
         
         return "invoice?faces-redirect=true";
     }
     
     public Double generatePST(){
         
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);         
        Client user = (Client) session.getAttribute("current_user");
        double pstRate = user.getProvince().getPSTrate().doubleValue();
                
        return Double.valueOf(String.format("%.2f", (generateTotal() * pstRate)));
     }
     
    public Double generateGST() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        double gstRate = user.getProvince().getGSTrate().doubleValue();

        return Double.valueOf(String.format("%.2f", (generateTotal() * gstRate)));
    }
    
    public Double generateHST() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        double hstRate = user.getProvince().getHSTrate().doubleValue();

        return Double.valueOf(String.format("%.2f", (generateTotal() * hstRate)));
    }
       
    
}
