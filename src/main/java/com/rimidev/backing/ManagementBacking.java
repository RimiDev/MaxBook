/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.controller.ReviewJpaController;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import com.rimidev.maxbook.entities.Review;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class ManagementBacking implements Serializable {

    private Logger logger = Logger.getLogger(BookDisplayBacking.class.getName());

    @Inject
    BookJpaController bkcon;
    @Inject
    ReviewJpaController revcon;
    @Inject
    InvoiceJpaController invcon;
    @Inject
    InvoiceDetailsJpaController invDetailsCon;

    private List<Book> bk;
    private List<Review> rev;
    private List<Invoice> inv;
    private List<Integer> status;
    private List<String> revStatuses;
    private Object selected;
//    private String message;
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void saveMessage() {
//        FacesContext context = FacesContext.getCurrentInstance();
//
//        context.addMessage(null, new FacesMessage("Successful", "Your message: " + message));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
//    }

    public List<Book> getBk() {
        return bk;
    }

    public void setBk(List<Book> bk) {
        this.bk = bk;
    }

    @PostConstruct
    public void init() {
        bk = bkcon.findBookEntities();
        rev = revcon.findReviewEntities();
        inv = invcon.findInvoiceEntities();

        revStatuses = new ArrayList<String>();
        revStatuses.add("Pending");
        revStatuses.add("Approved");

        status = new ArrayList<Integer>();
        status.add(0);
        status.add(1);
    }

    public List<Review> getRev() {
        return rev;
    }

    public void setRev(List<Review> rev) {
        this.rev = rev;
    }

    public void onRowAdd(RowEditEvent event) throws Exception {
        Book newBook = (Book) event.getObject();
        bkcon.create(newBook);
        FacesMessage msg = new FacesMessage("new Book");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) throws Exception {
        String item = "";
        String editType = "";
        if (event.getObject() instanceof Book) {
            onBookRowEdit(event);
            item = "Book " + ((Book) event.getObject()).getIsbn();
            editType = "Book Edit";
        } else if (event.getObject() instanceof Review) {
            onReviewRowEdit(event);
            item = "Review #" + ((Review) event.getObject()).getId().toString();
            editType = "Review Edit";
        }else if (event.getObject() instanceof Invoice) {
            onInvoiceRowEdit(event);
            item = "Invoice #" + ((Invoice) event.getObject()).getId();
            editType = "Invoice Edit";
        }else if (event.getObject() instanceof InvoiceDetails) {
            onInvDetailRowEdit(event);
            item = "Invoice Detail #" + ((InvoiceDetails) event.getObject()).getId();
            editType = "Invoice Details Edit";
        }
        
        FacesMessage msg = new FacesMessage(editType, item);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    private void onBookRowEdit(RowEditEvent event) throws Exception {
        Book editedItem = (Book) event.getObject();
        bkcon.edit(editedItem);
    }

    private void onInvoiceRowEdit(RowEditEvent event) throws Exception {
        Invoice editedItem  = (Invoice) event.getObject();
        invcon.edit(editedItem);
    }

    private void onInvDetailRowEdit(RowEditEvent event) throws Exception {
        InvoiceDetails editedItem = (InvoiceDetails) event.getObject();
        invDetailsCon.edit(editedItem);
    }

    private void onReviewRowEdit(RowEditEvent event) throws Exception {
        Review approvedReview = (Review) event.getObject();
        revcon.edit(approvedReview);
    }

    public void onBookRowRemove(String isbn) throws Exception {
        bkcon.destroy(isbn);
        bk = bkcon.findBookEntities();
    }
    
    public void onInvoiceRowRemove(Integer id) throws Exception {
        Invoice invoice = invcon.findInvoice(id);
        for(InvoiceDetails i:invoice.getInvoiceDetailsList()){
            invDetailsCon.destroy(i.getId());
        }
        invcon.destroy(id);
        inv = invcon.findInvoiceEntities();
    }
    
    public void onInvoiceDetailRowRemove(Integer id,Integer det) throws Exception {
        logger.log(Level.INFO,"Deleting invoice detail");
        Invoice i = invcon.findInvoice(id);
        logger.log(Level.INFO,"Invoice before"+i.getInvoiceDetailsList());
        invDetailsCon.destroy(det);
        BigDecimal newNet = new BigDecimal(0);
        BigDecimal newGross= new BigDecimal(0);
        
        for(InvoiceDetails invDet:i.getInvoiceDetailsList()){
            newNet.add(invDet.getBookPrice());
            Double d = invDet.getHSTrate().doubleValue();
            newGross = new BigDecimal(newNet.doubleValue()/(1-d));
        }
        i.setNetValue(newGross);
        i.setGrossValue(newGross);
        invcon.edit(i);
        inv = invcon.findInvoiceEntities();
        logger.log(Level.INFO,"Invoice after"+i.getInvoiceDetailsList());
         
    }
    
    

    public void onRowCancel(RowEditEvent event) {
        String item = "";
        if (event.getObject() instanceof Book) {
            item = "Book " + ((Book) event.getObject()).getIsbn();
        } else if (event.getObject() instanceof Review) {
            item = "Review #" + ((Review) event.getObject()).getId().toString();
        } else if (event.getObject() instanceof Invoice) {
            item = "Invoice #" + ((Invoice) event.getObject()).getId();
        } else if (event.getObject() instanceof InvoiceDetails) {
            item = "Invoice Detail #" + ((InvoiceDetails) event.getObject()).getId();
        }

        FacesMessage msg = new FacesMessage("Edit Cancelled", item);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {

        }
    }

    public void newLine(ActionEvent actionEvent) {
        this.bk.add(new Book());
        logger.log(Level.WARNING, "<<Book Inventory List: >>" + bk.get(bk.size() - 1));
    }

    public List<Integer> getStatus() {
        return status;
    }

    public List<String> getRevStatuses() {
        logger.log(Level.SEVERE, "Loading status options");
        return revStatuses;
    }

    public List<Invoice> getInv() {
        return inv;
    }

    public void setInv(List<Invoice> inv) {
        this.inv = inv;
    }

    public Object getSelected() {
        return selected;
    }

    public void setSelected(Object selected) {
        this.selected = selected;
    }

    public void deleteItem() throws Exception {
        bkcon.destroy(((Book) selected).getIsbn());
        selected = null;
    }

    public String checkStat(Integer stat) {
        if (stat.equals(1)) {
            return "Unavailable";
        }

        return "Available";
    }

}
