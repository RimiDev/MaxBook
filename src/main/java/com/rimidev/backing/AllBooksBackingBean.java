package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.entities.Book;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Backing Bean for the all books page to help manage data.
 * 
 * @author Philippe Langlois-Pedroso, 1542705
 */

@Named("allBooks")
@ApplicationScoped
public class AllBooksBackingBean implements Serializable {

    @Inject
    private BookJpaController bookController;

    private List<Book> model = null;
    private static final int DEFAULT_NUMBER_OF_RECORDS = 5;
    private static final int DEFAULT_PAGE_INDEX = 1;
    private int records;
    private int totalRecords;
    private int currentPageIndex;
    private int totalPages;
    private static final Logger logger = Logger.getLogger(AllBooksBackingBean.class.getName());

    /**
     * Updates the model based on the current configurations of the pagination.
     */
    public void updateModel() {
        int startId = getFirst();
        logger.info("Start Id: " +Integer.toString(startId));
        logger.info("List Number: " +Integer.toString(records));
        logger.info("Current Page Index: " +Integer.toString(currentPageIndex));
        if(records + startId > totalRecords){
            logger.info("updateModel -> LEFTOVERS");
            int leftOverAmount = totalRecords - startId + 1;
            logger.info("leftover amount: " +Integer.toString(leftOverAmount));
            setModel(bookController.findBookEntities(leftOverAmount, startId -1));
        }else{
            logger.info("updateModel -> FULL LIST");
            setModel(bookController.findBookEntities(records, startId -1));
        }
    }

    /**
     * Increment the page number up
     */
    public void next() {
        logger.info("NEXT WAS CLICKED");
        if (this.currentPageIndex < this.totalPages) {
            this.currentPageIndex++;
            updateModel();
        }
    }

    /**
     * Increment the page number down
     */
    public void prev() {
        logger.info("PREV WAS CLICKED");
        if (this.currentPageIndex > 1) {
            this.currentPageIndex--;
            updateModel();
        }
    }

    public int getRecords() {
        return records;
    }

    public int getRecordsTotal() {
        return totalRecords;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Based on configurations, return the id of the first book in the current
     * pagination group.
     * 
     * @return 
     */
    public int getFirst() {
        return (currentPageIndex * records) - records + 1;
    }

    /**
     * Method that will return the current pagination group of Books that need
     * to be displayed. If the model is null then a new model is instantiated.
     * 
     * @return List of the books to be displayed
     */
    public List<Book> getModel() {
        logger.info("AllBooksBackingBean -> getModel");
        if (model == null) {
            logger.info("model is null");
            this.records = DEFAULT_NUMBER_OF_RECORDS;
            this.currentPageIndex = DEFAULT_PAGE_INDEX;
            this.totalRecords = bookController.getBookCount();
            updateModel();
        }
        if(totalRecords % records == 0){
            this.totalPages = totalRecords / records;
        }else{
            this.totalPages = (totalRecords / records) + 1;
        }
        logger.info(Integer.toString(model.size()));
        return model;
    }

    /**
     * Sets the model property to a passed model.
     * 
     * @param model 
     */
    public void setModel(List<Book> model) {
        this.model = model;
    }

    /**
     * Sets the current Page index.
     * 
     * @param pageIndex 
     */
    public void setPageIndex(int pageIndex) {
        this.currentPageIndex = pageIndex;
    }
}
