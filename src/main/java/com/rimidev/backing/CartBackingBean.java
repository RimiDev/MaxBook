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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * Backing Bean for the cart page to help manage data.
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
        if (creditcard == null) {
            creditcard = new CreditCardBean();
        }
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

    /**
     * Adds a book based on it's isbn to the cart's Booklist.
     *
     * @param isbn
     */
    public void addToCart(String isbn) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        cart = (List<Book>) session.getAttribute("cartItems");
        if (cart == null) {
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

    /**
     * Removes the selected book from the session's cart book list.
     *
     * @param isbn
     * @return
     */
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

    /**
     * Generate the total cost to the client of the books in the cart.
     *
     * @return
     */
    public double generateTotal() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        cart = (List<Book>) session.getAttribute("cartItems");

        double total = 0.0;

        for (int i = 0; i < cart.size(); i++) {
            total += bookJpaController.isOnSale(cart.get(i)).doubleValue();
        }

        return Double.valueOf(String.format("%.2f", total));

    }

    /**
     * Returns the total tax of the cart.
     *
     * @return
     */
    public Double generateTaxTotal() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        double tax = user.getProvince().getHSTrate().doubleValue();

        double taxTotal = (generateTotal() * tax);
        return Double.valueOf(String.format("%.2f", taxTotal));

    }

    /**
     * Returns the total cost including the taxex on the cart.
     *
     * @return
     */
    public Double generateTotalTaxedSale() {
        return Double.valueOf(String.format("%.2f", (generateTotal() + generateTaxTotal())));
    }

    /**
     *
     *
     * @param id
     * @param dateSale
     * @param net
     * @param gross
     * @param books
     * @param country
     * @return
     * @throws Exception
     */
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
        session.setAttribute("clientInvoice", invoiceJpaController.clientMostRecentInvoice(user));
        BigDecimal gst = user.getProvince().getGSTrate();
        BigDecimal pst = user.getProvince().getPSTrate();
        BigDecimal hst = user.getProvince().getHSTrate();

        //Inserting to database
        for (int i = 0; i < books.size(); i++) {
            InvoiceDetails invd = new InvoiceDetails();

            invd.setBookPrice(bookJpaController.isOnSale(books.get(i)));
            invd.setGSTrate(gst);
            invd.setPSTrate(pst);
            invd.setHSTrate(hst);
            invd.setInvoiceId((Invoice) session.getAttribute("clientInvoice"));
            invd.setIsbn(books.get(i));

            invoiceDetailsController.create(invd);

            session.setAttribute("cartItems", new ArrayList<Book>());

        }

        return "invoice?faces-redirect=true";
    }

    /**
     * Reset the credit card values
     *
     * @return
     */
    public String resetCreditCardValues() {

        if (creditcard != null) {
            creditcard.setNumber("");
            creditcard.setName("");
            creditcard.setExp("");
            creditcard.setCv("");
        }

        return "cart?faces-redirect=true";
    }

    /**
     * Return the client's PST rate
     *
     * @return
     */
    public Double generatePST() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        double pstRate = user.getProvince().getPSTrate().doubleValue();

        return Double.valueOf(String.format("%.2f", (generateTotal() * pstRate)));
    }

    /**
     * Return the client's GST rate
     *
     * @return
     */
    public Double generateGST() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        double gstRate = user.getProvince().getGSTrate().doubleValue();

        return Double.valueOf(String.format("%.2f", (generateTotal() * gstRate)));
    }

    /**
     * Return the client's HST rate
     *
     * @return
     */
    public Double generateHST() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        double hstRate = user.getProvince().getHSTrate().doubleValue();

        return Double.valueOf(String.format("%.2f", (generateTotal() * hstRate)));
    }

    /**
     * If the session variable for the cart is not created, create it.
     *
     * @return
     * @throws Exception
     */
    public String emptyCartIntoCartVar() throws Exception {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client user = (Client) session.getAttribute("current_user");
        if (user == null) {
            return "login?faces-redirect=true";
        } else {
            if (user.getAddress1() == null || user.getCountry() == null
                    || user.getFirstName() == null || user.getLastName() == null
                    || user.getPostalCode() == null || user.getPhoneNumber() == null
                    || user.getCity() == null || user.getProvince() == null) {
                return "account-details?faces-redirect=true";
            }
        }
        List<Book> sCart = (List<Book>) session.getAttribute("cartItems");

        this.cart = sCart;

        return "checkout?faces-redirect=true";
    }

}
