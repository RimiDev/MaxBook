/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.beans;

/**
 *
 * @author maximelacasse
 */
public class BestSellingBooksBean {
    
    private String books;
    
    public BestSellingBooksBean(String books){
        
        this.books = books;
        
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
    
    
    
}
