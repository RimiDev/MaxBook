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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRevMessage() {
        return revMsg;
    }

    public void setRevMessage(String revMsg) {
        this.revMsg = revMsg;
    }

    public void addReview(Book bk) throws Exception {
        //rev.setReviewMessage(msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        isbn = params.get("isbn");
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client curr_user = (Client) session.getAttribute("current_user");
        
        rev.setClientId(curr_user);
        rev.setIsbn(bk);
        rev.setApprovalStatus("Pending");
        rev.setRating(rating);
        //rev.setReviewDate();
        revControl.create(rev);
    }
}
