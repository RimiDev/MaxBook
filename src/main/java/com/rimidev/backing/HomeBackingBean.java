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
 * Backing Bean for the home page to help manage data.
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

    /**
     * Return a list of the top selling books.
     * 
     * @return 
     */
    public List<Book> getTopSellingBooks() {
        if (topSellingBooks == null) {
            topSellingBooks = invoiceDetails.getTopSellingBooks();
        }
        return topSellingBooks;
    }

    /**
     * Return a list of recently sold books
     * 
     * @return 
     */
    public List<Book> getRecentSoldBooks() {
        if (recentSoldBooks == null) {
            recentSoldBooks = invoiceDetails.getRecentSoldBook();
        }
        return recentSoldBooks;
    }

    /**
     * Return a String if details based on the rss feed.
     * 
     * @return 
     */
    public String getActiveNews() {
        List<News> ns = news.findNewsEntities().stream()
        .filter(i -> i.getActiveStatus() == true).collect(Collectors.toList());
        
        return ns.get(0).getReaderLink();
    }
}
