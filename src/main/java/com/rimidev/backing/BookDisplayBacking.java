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
import java.util.Map;
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

    public String showDetails(String isbn) {

        hideReview();
        logger.log(Level.INFO, "Book Isbn before>>> " + this.isbn);
        this.isbn = isbn;

        logger.log(Level.INFO, "Book Isbn>>> after" + this.isbn);

        return "bookDetails";
    }

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


    public Double checkSales(int salePrice) {
        
        return 0.0;
    }

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
    
    public String displayTitle(String title){
        
        if (title.length()>50){
            return title.substring(0,50) + "...";
        }
       return title;
    }
    
    public List<Review> showPermittedReviews(List<Review> allReviews){
        logger.info("booyaka");
        return allReviews.stream().filter(r -> r.getApprovalStatus().equalsIgnoreCase("accepted")).collect(Collectors.toList());
    }
}
