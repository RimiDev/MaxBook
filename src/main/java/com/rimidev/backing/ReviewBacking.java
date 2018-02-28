/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.ReviewJpaController;
import com.rimidev.maxbook.entities.Review;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

    public void addReview() throws Exception {
        //rev.setReviewMessage(msg);
        rev.setApprovalStatus("Pending");
        rev.setRating(rating);
        //rev.setReviewDate(new TimeStamp(System.currentTimeMillis()));
        revControl.create(rev);
    }
}
