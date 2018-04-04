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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author maximelacasse
 */
@Named
@SessionScoped
public class SearchBackingBean implements Serializable{

    @Inject
    private BookJpaController bookJPA;
    @Inject
    private AuthorJpaController authorJPA;

    private Logger logger = Logger.getLogger(ClientJpaController.class.getName());

    private String title;
    private String genre;
    private String firstName;
    private String lastName;
    private String fullName;
    private String searchCriteria;

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<Author> getSearchedAuthors() {
        return searchedAuthors;
    }

    public void setSearchedAuthors(List<Author> searchedAuthors) {
        this.searchedAuthors = searchedAuthors;
    }

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

        if (searchedAuthors.size() > 0) {
            searchedBooks = searchedAuthors.get(0).getBookList();
        }

        return searchedBooks;
    }

    public List<Book> getBookByLastName() {
        searchedAuthors = authorJPA.getBookByLastName(lastName);

        if (searchedAuthors.size() > 0) {
            searchedBooks = searchedAuthors.get(0).getBookList();
        }

        return searchedBooks;
    }

    //Getters & Setters
    public List<String> getFullName(Book book) {

        List<String> authorNameList = new ArrayList<>();

        if (book.getAuthorList().size() >= 1) {
            for (int i = 0; i < book.getAuthorList().size(); i++) {
                if (i == 1){
                    authorNameList.add(" , ");
                }
                authorNameList.add(
                        book.getAuthorList().get(i).getFirstName()
                        + " "
                        + book.getAuthorList().get(i).getLastName()
                        );
                
                
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
        if (searchedBooks == null) {
            searchedBooks = bookJPA.getBookByTitle("");
        }
        
        return searchedBooks;
    }

    public void setSearchedBooks(List<Book> searchedBooks) {
        this.searchedBooks = searchedBooks;
    }

    public String searchBooks() {
        searchedBooks = new ArrayList<>(bookJPA.searchBooks(searchCriteria));
        logger.log(Level.INFO, "Books by search criteria (search Backing)>> " + searchedBooks.size());
        if (searchedBooks.size() == 1) {
            BookDisplayBacking bkDp = new BookDisplayBacking();
            logger.log(Level.INFO, "List size 1>> Displaying Book " + searchedBooks.get(0).getIsbn());

            
            bkDp.showDetails(searchedBooks.get(0).getIsbn().toString());
            
        }
        return "advancedSearch";
    }

}
