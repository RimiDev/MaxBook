/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.ReviewJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Review;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class ReviewBacking implements Serializable {

    private Logger logger = Logger.getLogger(BookDisplayBacking.class.getName());

    @Inject
    private ReviewJpaController revControl;
    private Review rev;

    private Integer rating;
    private String revMsg;
    private String isbn;

    public Integer getrating() {
        return rating;
    }

    public void setrating(Integer rating) {
        this.rating = rating;
    }

    public String getrevMsg() {
        return revMsg;
    }

    public void setrevMsg(String revMsg) {
        this.revMsg = revMsg;
    }

    public String addReview() throws Exception {

        //logger.log(Level.INFO, "Book >>> "+bk);
        rev = new Review();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client curr_user = (Client) session.getAttribute("current_user");

        FaceletContext faceletContext = (FaceletContext) FacesContext.getCurrentInstance().getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
        Book bk = (Book) faceletContext.getAttribute("bookie");

        logger.log(Level.INFO, "Book >>> " + bk);
        logger.log(Level.INFO, "User >>> " + curr_user);
        logger.log(Level.INFO, "Review Rating >>> " + rating);
        logger.log(Level.INFO, "Review Message >>> " + revMsg);
        
        rev.setClientId(curr_user);
        rev.setIsbn(bk);
        rev.setApprovalStatus("Pending");
        rev.setRating(rating);
        rev.setReviewMessage(revMsg);

        Date current_date = new Date();
        

        logger.log(Level.INFO, "Review Date >>> " + current_date.toString());

        rev.setReviewDate(current_date);
        revControl.create(rev);

        logger.log(Level.INFO, "<<< Review Created >>> ");
        
        rating = 0;
        revMsg="";
        return "bookDetails.xhtml?faces-redirect=true";

    }
}
