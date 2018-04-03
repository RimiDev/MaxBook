/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.NewsJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.News;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author maximelacasse
 */
@Named
@RequestScoped
public class HomeBackingBean {

    @Inject
    private InvoiceDetailsJpaController invoiceDetails;
    @Inject
    private NewsJpaController news;

    private List<Book> topSellingBooks;

    private List<Book> recentSoldBooks;

    public List<Book> getTopSellingBooks() {
        if (topSellingBooks == null) {
            topSellingBooks = invoiceDetails.getTopSellingBooks();
        }
        return topSellingBooks;
    }

    public List<Book> getRecentSoldBooks() {
        if (recentSoldBooks == null) {
            recentSoldBooks = invoiceDetails.getRecentSoldBook();
        }
        return recentSoldBooks;
    }

    public String getActiveNews() {
        List<News> ns = news.findNewsEntities().stream()
        .filter(i -> i.getActiveStatus() == true).collect(Collectors.toList());
        
        return ns.get(0).getReaderLink();
    }
}
