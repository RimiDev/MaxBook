package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Author;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Review;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * Backing Bean for the book details page to help manage data.
 * 
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class BookDisplayBacking implements Serializable {

    private Logger logger = Logger.getLogger(BookDisplayBacking.class.getName());

    @Inject
    private BookJpaController bookjpaControl;
    private String isbn = "";
    private Book book;
    private String visibilityStyle = "";

    public String getVisibilityStyle() {
        return visibilityStyle;
    }

    public void setVisibilityStyle(String visibiltyStyle) {
        this.visibilityStyle = visibiltyStyle;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Changes the displayed book details page based on the passed isbn.
     * 
     * @param isbn
     * @return 
     */
    public String showDetails(String isbn) {

        hideReview();
        logger.log(Level.INFO, "Book Isbn before>>> " + this.isbn);
        this.isbn = isbn;

        logger.log(Level.INFO, "Book Isbn>>> after" + this.isbn);

        return "bookDetails";
    }

    /**
     * Checks if the selected book exists.
     * 
     * @param event 
     */
    public void checkBookExist(ComponentSystemEvent event) {
        //need to fix reidrect where i cant go directly to book details page
        logger.log(Level.INFO, "We in checkBookExist");
        logger.log(Level.INFO, "isbn " + this.isbn);
        if (isbn == null || isbn.isEmpty()) {
            logger.log(Level.INFO, "isbn " + this.isbn);

            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            handler.performNavigation("404");
        }

    }

    /**
     * Based on a list of authors, return a list of books written.
     * 
     * @param auths
     * @return 
     */
    public List<Book> getRecommendationsByAuthor(List<Author> auths) {

        List<Integer> authIds = new ArrayList<Integer>();
        for (Author au : auths) {
            authIds.add(au.getId());
        }

        List<Book> recBooks = bookjpaControl.getBooksByAuthor(authIds, isbn);

        Collections.shuffle(recBooks);
        if (recBooks.size() >= 4) {
            recBooks = recBooks.subList(0, 4);
        } else {
            recBooks = recBooks.subList(0, recBooks.size());
        }
        logger.log(Level.INFO, "AUTHOR Four recs: " + recBooks);
        
        if(recBooks.size() > 3){
            return recBooks.subList(0, 2);
        }

        return recBooks;
    }

    /**
     * Returns a list of books that are of a passed genre.
     * 
     * @param genre
     * @return 
     */
    public List<Book> getRecommendationsByGenre(String genre) {
        book = bookjpaControl.findBook(isbn);
        List<Book> recBooks = bookjpaControl.getBookByGenre(book.getGenre());
        recBooks.remove(book);

        if (recBooks.size() >= 4) {
            recBooks = recBooks.subList(0, 4);
        } else {
            recBooks = recBooks.subList(0, recBooks.size());
        }
        logger.log(Level.INFO, "GENRE Four recs: " + recBooks);

        if(recBooks.size() > 3){
            return recBooks.subList(0, 2);
        }
        
        return recBooks;

    }

    /**
     * Hides the review on the page if not approved by a manager.
     */
    public void hideReview() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Client curr_user = (Client) session.getAttribute("current_user");

        logger.log(Level.INFO, "Currently logged in: " + curr_user);

        if (curr_user == null) {
            visibilityStyle = "hideRevStyle";
        } else {
            visibilityStyle = "";
        }

    }
    
    /**
     * Display the title of a book that is shortened to 50 characters.
     * 
     * @param title
     * @return 
     */
    public String displayTitle(String title){
        
        if (title.length()>50){
            return title.substring(0,50) + "...";
        }
       return title;
    }
    
    /**
     * Returns a list of reviews that have been approved by a manager.
     * 
     * @param allReviews
     * @return 
     */
    public List<Review> showPermittedReviews(List<Review> allReviews){
        logger.info("booyaka");
        return allReviews.stream().filter(r -> r.getApprovalStatus().equalsIgnoreCase("approved")).collect(Collectors.toList());
    }
}
