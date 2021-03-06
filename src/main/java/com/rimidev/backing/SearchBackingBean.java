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
 * @author maxime lacasse
 * @author Rhai Hinds
 * @author Eric Hughes
 */
@Named
@SessionScoped
public class SearchBackingBean implements Serializable {

  @Inject
  private BookJpaController bookJPA;
  @Inject
  private AuthorJpaController authorJPA;

  @Inject
  private BookDisplayBacking bookDisplayBacking;

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
  public String getBookByTitle() {
    genre = "";
    lastName = "";
    firstName = "";
    searchedBooks = bookJPA.getBookByTitle(title);

    return "advancedSearch";
  }

  public String getBookByGenre() {

    searchedBooks = bookJPA.getBookByGenre(genre);
    title = "";
    lastName = "";
    firstName = "";
    return "advancedSearch";
  }

  public String getBookByFirstName() {
    searchedAuthors = authorJPA.getBookByFirstName(firstName);
    genre = "";
    if (searchedAuthors.size() > 0) {
      searchedBooks = searchedAuthors.get(0).getBookList();
    }
    title = "";
    return "advancedSearch";
  }

  public String getBookByLastName() {
    searchedAuthors = authorJPA.getBookByLastName(lastName);

    if (searchedAuthors.size() > 0) {
      searchedBooks = searchedAuthors.get(0).getBookList();
    }

    return "advancedSearch";
  }

  //Getters & Setters
  public List<String> getFullName(Book book) {

    List<String> authorNameList = new ArrayList<>();

    if (book.getAuthorList().size() >= 1) {
      for (int i = 0; i < book.getAuthorList().size(); i++) {
        if (i == 1) {
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

    return searchedBooks;
  }

  public void setSearchedBooks(List<Book> searchedBooks) {
    this.searchedBooks = searchedBooks;
  }

  public String searchBooks() {
    searchedBooks = new ArrayList<>(bookJPA.searchBooks(searchCriteria));
    logger.log(Level.INFO, "Books by search criteria (search Backing)>> " + searchedBooks.size());
    if (searchedBooks.size() == 1) {
      //bookDisplayBacking.setIsbn(searchedBooks.get(0).getIsbn())
      logger.log(Level.INFO, "List size 1>> Displaying Book " + searchedBooks.get(0).getIsbn());
      return bookDisplayBacking.showDetails(searchedBooks.get(0).getIsbn());

    }
    return "advancedSearch";
  }

}
