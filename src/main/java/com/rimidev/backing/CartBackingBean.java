/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.beans.CreditCardBean;
import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Inject
    private InvoiceDetailsJpaController invoiceDetailsController;
    
    private CreditCardBean creditcard;

    public CreditCardBean getCreditcardBean() {
        if (creditcard == null)
            creditcard = new CreditCardBean();
        return creditcard;
    }

    public void setCreditcardBean(CreditCardBean creditcard) {
        this.creditcard = creditcard;
    }

    public List<Book> getCart() {
        return cart;
    }

    public void setCart(List<Book> cart) {
        this.cart = cart;
    }
    

    public void addToCart(String isbn) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        cart = (List<Book>) session.getAttribute("cartItems");
        if (cart == null){
          session.setAttribute("cartItems", new ArrayList<Book>());
          cart = (List<Book>) session.getAttribute("cartItems");
        }
        Book book = bookJpaController.findBook(isbn);
        Boolean bookAlreadyInCart = false;

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getIsbn().equals(book.getIsbn())) {
                bookAlreadyInCart = true;
                break;
            }
        }

        if (!bookAlreadyInCart) {
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
            if (next.getIsbn().equals(isbn)) {
                cart.remove(next);
                break;
            }
        }
        session.setAttribute("cartItems", cart);

        return null;

    }

    public double generateTotal() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        cart = (List<Book>) session.getAttribute("cartItems");

        double total = 0.0;

        for (int i = 0; i < cart.size(); i++) {
            total += cart.get(i).getSalePrice().doubleValue();
        }

        return Double.valueOf(String.format("%.2f", total));

    }

    public Double generateTaxTotal() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        double tax = user.getProvince().getHSTrate().doubleValue();

        double taxTotal = (generateTotal() * tax);
        return Double.valueOf(String.format("%.2f", taxTotal));

    }

    public Double generateTotalTaxedSale() {

        return Double.valueOf(String.format("%.2f", (generateTotal() + generateTaxTotal())));
    }

    public String validateCreditCardInformation(Client id, Timestamp dateSale, BigDecimal net, BigDecimal gross,
                  List<Book> books, String country) throws Exception {

        //insert invoice to database
        Invoice inv = new Invoice();
        
        inv.setClientId(id);
        inv.setDateOfSale(new Date());
        inv.setGrossValue(gross);
        inv.setNetValue(net);

        invoiceJpaController.create(inv);
        
        //Province taxes
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        
        BigDecimal gst =  user.getProvince().getGSTrate();
        BigDecimal pst =  user.getProvince().getPSTrate();
        BigDecimal hst =  user.getProvince().getHSTrate();
        
        //Inserting to database
        for (int i = 0; i < books.size(); i++){
            InvoiceDetails invd = new InvoiceDetails();
            
            invd.setBookPrice(books.get(i).getSalePrice());
            invd.setGSTrate(gst);
            invd.setPSTrate(pst);
            invd.setHSTrate(hst);
            invd.setInvoiceId(inv);
            invd.setIsbn(books.get(i));
            
            invoiceDetailsController.create(invd);
            
            session.setAttribute("cartItems", new ArrayList<Book>());
            
        }
        

        return "invoice?faces-redirect=true";
    }
    
    public String resetCreditCardValues(){
        
        if (creditcard != null){
            creditcard.setNumber("");
            creditcard.setName("");
            creditcard.setExp("");
            creditcard.setCv("");
        }
            
            return "cart?faces-redirect=true";
    }

    public Double generatePST() {

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
    
    public String emptyCartIntoCartVar() throws Exception {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        if (user == null){
          return "login?faces-redirect=true";
        }
        
        
        
        List<Book> sCart = (List<Book>) session.getAttribute("cartItems");        
        
        this.cart = sCart;
    
        return "checkout?faces-redirect=true";
    }

}
