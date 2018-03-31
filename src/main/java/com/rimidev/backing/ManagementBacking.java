package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.controller.ReviewJpaController;
import com.rimidev.maxbook.entities.Review;
import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.NewsJpaController;
import com.rimidev.maxbook.controller.SurveyJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import com.rimidev.maxbook.entities.News;
import com.rimidev.maxbook.entities.Survey;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 * Backing Bean for the management section to help manage data.
 * 
 * @author Rhai Hinds
 * @author Eric Hughes
 * @author Philippe Langlois-Pedroso
 */
@Named
@SessionScoped
public class ManagementBacking implements Serializable {

    private Logger logger = Logger.getLogger(BookDisplayBacking.class.getName());

    @Inject
    BookJpaController bkcon;
    @Inject
    ReviewJpaController revcon;
    @Inject
    InvoiceJpaController invcon;
    @Inject
    InvoiceDetailsJpaController invDetailsCon;
    @Inject
    NewsJpaController newsCon;

    private List<Book> bk;
    private List<Review> rev;
    private List<Invoice> inv;
    private List<News> news;
    private List<Integer> approvalStatus;
    private List<String> revStatuses;
    private List<Boolean> newsStatus;
    private Book selectedBook;
    private boolean statusBool;

    @Inject
    ClientJpaController clientJpaController;
    List<Client> clients;

    @Inject
    SurveyJpaController surveyJpaController;
    List<Survey> surveys;

    Survey newSurvey;

    public List<Client> getClients() {
        if (clients == null) {
            clients = clientJpaController.findClientEntities();
        }
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @PostConstruct
    public void init() {
        bk = bkcon.findBookEntities();
        clients = clientJpaController.findClientEntities();
        surveys = surveyJpaController.findSurveyEntities();
        rev = revcon.findReviewEntities();
        inv = invcon.findInvoiceEntities();
        news = newsCon.findNewsEntities();
        revStatuses = new ArrayList<String>();
        revStatuses.add("Pending");
        revStatuses.add("Approved");
        
        newsStatus = new ArrayList<Boolean>();
        newsStatus.add(true);
        newsStatus.add(false);

        approvalStatus = new ArrayList<Integer>();
        newSurvey = new Survey();
        approvalStatus.add(0);
        approvalStatus.add(1);
    }

    public List<Review> getRev() {
        return rev;
    }

    public void setRev(List<Review> rev) {
        this.rev = rev;
    }

    public void onRowEdit(RowEditEvent event) throws Exception {
        String item = "";
        String editType = "";
        if (event.getObject() instanceof Book) {
            onBookRowEdit(event);
            item = "Book " + ((Book) event.getObject()).getIsbn();
            editType = "Book Edit";
        } else if (event.getObject() instanceof Review) {
            onReviewRowEdit(event);
            item = "Review #" + ((Review) event.getObject()).getId().toString();
            editType = "Review Edit";
        } else if (event.getObject() instanceof Invoice) {
            onInvoiceRowEdit(event);
            item = "Invoice #" + ((Invoice) event.getObject()).getId();
            editType = "Invoice Edit";
        } else if (event.getObject() instanceof InvoiceDetails) {
            onInvDetailRowEdit(event);
            item = "Invoice Detail #" + ((InvoiceDetails) event.getObject()).getId();
            editType = "Invoice Details Edit";

        } else if (event.getObject() instanceof Client) {
            editClient((Client) event.getObject());
            item = "Client Id" + ((Client) event.getObject()).getId();
            editType = "Client Edit";
        } else if (event.getObject() instanceof Survey) {
            editSurvey((Survey) event.getObject());
////      item = "Survey Id" + ((Survey) event.getObject()).getId();
            editType = "Survey Edit";
        } else if (event.getObject() instanceof News) {
            onNewsRowEdit(event);
            item = "News Id" + ((News) event.getObject()).getId();
            editType = "News Edit";
        }

        FacesMessage msg = new FacesMessage(editType, item);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Book> getBk() {
        return bk;
    }

    public boolean checkStatus(Character status) {
        if (status == '0') {
            return false;
        }
        return true;
    }

    public void setBk(List<Book> bk) {
        this.bk = bk;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }

    public Survey getNewSurvey() {
        return this.newSurvey;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Boolean> getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(List<Boolean> newsStatus) {
        this.newsStatus = newsStatus;
    }
    

    public void onRowAdd(RowEditEvent event) throws Exception {
        Book newBook = (Book) event.getObject();
        bkcon.create(newBook);
        FacesMessage msg = new FacesMessage("Book Created", String.valueOf(newBook));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void onBookRowEdit(RowEditEvent event) throws Exception {
        Book editedItem = (Book) event.getObject();
        bkcon.edit(editedItem);
    }

    private void onInvoiceRowEdit(RowEditEvent event) throws Exception {
        Invoice editedItem = (Invoice) event.getObject();
        invcon.edit(editedItem);
    }

    private void onInvDetailRowEdit(RowEditEvent event) throws Exception {
        InvoiceDetails editedItem = (InvoiceDetails) event.getObject();
        invDetailsCon.edit(editedItem);
    }

    private void onReviewRowEdit(RowEditEvent event) throws Exception {
        Review approvedReview = (Review) event.getObject();
        revcon.edit(approvedReview);
    }

    private void onNewsRowEdit(RowEditEvent event) throws Exception{
        News newsLink = (News) event.getObject();
        newsCon.edit(newsLink);
    }

    public void onRowCancel(RowEditEvent event) throws Exception {
        String item = "";
        if (event.getObject() instanceof Book) {
            item = "Book " + ((Book) event.getObject()).getIsbn();
        } else if (event.getObject() instanceof Review) {
            item = "Review #" + ((Review) event.getObject()).getId().toString();
        } else if (event.getObject() instanceof Invoice) {
            item = "Invoice #" + ((Invoice) event.getObject()).getId();
        } else if (event.getObject() instanceof InvoiceDetails) {
            item = "Invoice Detail #" + ((InvoiceDetails) event.getObject()).getId();
        } else if (event.getObject() instanceof Client) {
            item = "Client Id" + ((Client) event.getObject()).getId();
        } else if (event.getObject() instanceof Survey) {
            item = "Survey Id" + ((Survey) event.getObject()).getId();
        }else if (event.getObject() instanceof News) {
            item = "News Id" + ((News) event.getObject()).getId();
        }

        FacesMessage msg = new FacesMessage("Edit Cancelled", item);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {

        }

    }

    public void newLine() {
        this.bk.add(new Book());
        logger.log(Level.WARNING, "<<Book Inventory List: >>" + bk.get(bk.size() - 1));
    }

    public List<Integer> getStatus() {
        return approvalStatus;
    }

    public List<String> getRevStatuses() {
        logger.log(Level.SEVERE, "Loading status options");
        return revStatuses;
    }

    public List<Invoice> getInv() {
        return inv;
    }

    public void setInv(List<Invoice> inv) {
        this.inv = inv;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selected) {
        this.selectedBook = selected;
    }

    public void deleteItem() throws Exception {
        bkcon.destroy(((Book) selectedBook).getIsbn());
        selectedBook = null;
    }

    public String checkStat(Integer stat) {
        if (stat.equals(1)) {
            return "Available";
        }

        return "UnAvailable";
    }

    /**
     * client management
     */
    private void editClient(Client c) throws Exception {

        clientJpaController.edit(c);
        FacesMessage msg = new FacesMessage("Client Edited", String.valueOf(c));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public BigDecimal getTotalValueOfAllPurchases(Client c) {

        double t = 0;
        for (Invoice invoice : c.getInvoiceList()) {
            logger.log(Level.WARNING, invoice.getGrossValue().toString());
            t += invoice.getGrossValue().doubleValue();

        }

        t = Math.ceil(t);
        return new BigDecimal(t);
    }

    /**
     * Edit of a Survey entry in the database
     *
     * @param survey
     * @throws Exception
     */
    public void editSurvey(Survey survey) throws Exception {
        surveyJpaController.edit(survey);

    }

    public void addSurvey() throws Exception {
        logger.info(newSurvey.getQuestion());
        logger.info(newSurvey.getOption1());
        logger.info(newSurvey.getOption2());
        logger.info(newSurvey.getOption3());
        logger.info(newSurvey.getOption4());
        surveyJpaController.create(newSurvey);
        logger.info("New Survey Added");
    }

    public void deleteSurvey() {
        logger.info("Test Delete Survey");
    }

    public void updateBook(Book b) throws Exception {
        bkcon.edit(b);
        logger.info("UPDATED BOOK");
    }
    
    public void resetActive(Integer id) throws Exception{
        for(News ns: news){
            if(!ns.getId().equals(id)){
                ns.setActiveStatus(false);
                newsCon.edit(ns);
            }
        }
        news = newsCon.findNewsEntities();
    }
    
}
