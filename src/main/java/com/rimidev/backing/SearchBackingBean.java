/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.AuthorJpaController;
import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.entities.Author;
import com.rimidev.maxbook.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author maximelacasse
 */
@Named
@RequestScoped
public class SearchBackingBean {
    
    @Inject
    private BookJpaController bookJPA;
    @Inject
    private AuthorJpaController authorJPA;
    

    private String title;
    private String genre;
    private String firstName;
    private String lastName;
    private String fullName;
  
    private List<Book> searchedBooks;
    private List<Author> searchedAuthors;
    
    //Queries

    public List<Book> getBookByTitle() {

            searchedBooks = bookJPA.getBookByTitle(title); 

        return searchedBooks;
    }
    
    public List<Book> getBookByGenre() {

            searchedBooks = bookJPA.getBookByGenre(genre);

        return searchedBooks;
    }
    
    public List<Book> getBookByFirstName() {
            searchedAuthors = authorJPA.getBookByFirstName(firstName);
            
            if (searchedAuthors.size() > 0){
            searchedBooks = searchedAuthors.get(0).getBookList();
            }
            
            return searchedBooks;
    }
    
    public List<Book> getBookByLastName() {
            searchedAuthors = authorJPA.getBookByLastName(lastName);
            
            if (searchedAuthors.size() > 0){
            searchedBooks = searchedAuthors.get(0).getBookList();
            }
            
            return searchedBooks;
    }    
    
    //Getters & Setters
    
        public List<String> getFullName(Book book) {
            
            List<String> authorNameList = new ArrayList<>();
            
            if (book.getAuthorList().size() >= 1){
                for (int i = 0; i < book.getAuthorList().size(); i++){
                    authorNameList.add(
                            book.getAuthorList().get(i).getFirstName()                           
                            + " " +
                            book.getAuthorList().get(i).getLastName());
                    
                }
            }
           
        return authorNameList;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<Author> getsearchedAuthors() {
        return searchedAuthors;
    }

    public List<Book> getSearchedBooks() {
      if (searchedBooks == null)  
        searchedBooks = bookJPA.getBookByTitle("");   
      
        return searchedBooks;
    }

    public void setSearchedBooks(List<Book> searchedBooks) {
        this.searchedBooks = searchedBooks;
    }
    
    public void searchQuery(){
        bookJPA.searchBooks("");
    }
    
    

    
    
    
}
