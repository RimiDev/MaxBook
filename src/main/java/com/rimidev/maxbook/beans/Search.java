/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author maximelacasse
 */
@Named("search")
@RequestScoped
public class Search {
    
    private String searchType = "title";
    private String searchQuery;
    private String title;
    private String genre;
    private String author;
    private String publisher;
    
    
     
    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
    
    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    

    /**
     * Creates a new instance of Search
     */
    public Search() {
    }
    
    
    
}
