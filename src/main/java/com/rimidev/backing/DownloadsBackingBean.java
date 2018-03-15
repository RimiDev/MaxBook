package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Philippe Langlois-Pedroso, 1542705
 */
@Named
@RequestScoped
public class DownloadsBackingBean implements Serializable{
    
    @Inject
    private BookJpaController bookJpaController;
    @Inject
    private InvoiceJpaController invoiceController;
    @Inject
    private InvoiceDetailsJpaController invoiceDetailsController;
    @Inject
    private ClientJpaController clientController;

    private static final Logger logger = Logger.getLogger(DownloadsBackingBean.class.getName());
    
    private List<Book> allDownloads;
    private List<Book> model = null;
    private List<Integer> pages = null;
    private int startIndex;
    private int endIndex;
    private int records;
    private int totalRecords;
    private int currentPageIndex;
    private int totalPages;
    private HttpSession session;
    
    public List<Book> tester(){
        logger.info("INSIDE TESTER");
        Invoice invoice = invoiceController.findInvoice(1);
        logger.info("INVOICE ID: " +invoice.getId());
        List<InvoiceDetails> details = invoice.getInvoiceDetailsList();
        for(InvoiceDetails d : details){
            logger.info(d.getIsbn().getIsbn());
        }
        return null;
    }
    
    public List<Book> getAllOwnedBooks(int clientId){
        Client c = clientController.findClient(clientId);
        List<Invoice> invoices = c.getInvoiceList();
        logger.info("Number of Associated Invoices: " +invoices.size());
        List<InvoiceDetails> details = new ArrayList();
        for(Invoice i : invoices){
            details.addAll(i.getInvoiceDetailsList());
        }
        for(InvoiceDetails d : details){
            logger.info(d.getIsbn().getIsbn());
        }
        return null;
    }
    
    /**
     * Updates the model based on the current configurations of the pagination.
     */
    public void updateModel() {
        startIndex = getFirst();
        endIndex = startIndex + records;
        logger.info("Start Id: " +Integer.toString(startIndex));
        logger.info("List Number: " +Integer.toString(records));
        logger.info("Current Page Index: " +Integer.toString(currentPageIndex));
        if(records + startIndex > totalRecords){
            logger.info("updateModel -> LEFTOVERS");
            setModel(allDownloads.subList(startIndex, totalRecords - 1));
        }else{
            logger.info("updateModel -> FULL LIST");
            setModel(allDownloads.subList(startIndex, endIndex));
        }
    }
    
    /**
     * Increment the page number up
     */
    public void next() {
        logger.info("NEXT WAS CLICKED");
        if (this.currentPageIndex < this.totalPages) {
            this.currentPageIndex++;
        }
        updateModel();
    }

    /**
     * Increment the page number down
     */
    public void prev() {
        logger.info("PREV WAS CLICKED");
        if (this.currentPageIndex > 1) {
            this.currentPageIndex--;
        }
        updateModel();
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
            getAllOwnedBooks(1);
            this.records = 4;
            this.currentPageIndex = 1;
            this.startIndex = 0;
            this.totalRecords = allDownloads.size();
//            this.model = bookJpaController.findBookEntities(4, 1);
//            logger.info(Integer.toString(allDownloads.size()));
            if(allDownloads.size() == 0){
                return new ArrayList();
            }
            if(allDownloads.size() < records){
                endIndex = allDownloads.size() - 1;
            }else{
                endIndex = this.records - 1;
            }
            this.model = (List<Book>) allDownloads.subList(startIndex, endIndex);
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
    
    public void getDownload(){
        logger.info("Downloading Book");
    }
}
