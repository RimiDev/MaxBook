/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.ReviewJpaController;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Review;
import java.io.Serializable;
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
    private List<Book> bk;
    private List<Review>rev;
    private List<Integer> status;
    private List<String> revStatuses;
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
         FacesMessage msg = new FacesMessage("Book Created", String.valueOf(newBook));
        FacesContext.getCurrentInstance().addMessage(null, msg);       
    }

    
    public void onRowEdit(RowEditEvent event) throws Exception {
        Book editedBook = (Book) event.getObject();
        bkcon.edit(editedBook);
        FacesMessage msg = new FacesMessage("Book Edited", String.valueOf(editedBook));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
    
     public void onReviewRowEdit(RowEditEvent event) throws Exception {
        Review approvedReview = (Review) event.getObject();
        revcon.edit(approvedReview);
        FacesMessage msg = new FacesMessage("Review Edited", "Review #"+((Review) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowRemove(RowEditEvent event) {

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Book) event.getObject()).getIsbn());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
      public void onReviewRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "Review #"+((Review) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {

        }
    }
    
    public void newLine(ActionEvent actionEvent){
        this.bk.add(new Book());
        logger.log(Level.WARNING, "<<Book Inventory List: >>"+bk.get(bk.size()-1));
    }
    
    public List<Integer> getStatus(){
        return status;
    }
    
     public List<String> getRevStatuses() {
        logger.log(Level.SEVERE, "Loading status options");
        return revStatuses;
    }
    
    public String checkStat(Integer stat){
        if(stat.equals(1)){
            return "Unavailable";
        }
        
        return "Available";
    }
    
    
}
