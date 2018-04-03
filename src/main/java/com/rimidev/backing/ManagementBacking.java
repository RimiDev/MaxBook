/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.controller.ReviewJpaController;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import com.rimidev.maxbook.entities.Review;
import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.NewsJpaController;
import com.rimidev.maxbook.controller.PublisherJpaController;
import com.rimidev.maxbook.controller.SurveyJpaController;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import com.rimidev.maxbook.entities.News;
import com.rimidev.maxbook.entities.Publisher;
import com.rimidev.maxbook.entities.Survey;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class ManagementBacking implements Serializable {

    private Logger logger = Logger.getLogger(BookDisplayBacking.class.getName());
    private FacesContext context;
    private ResourceBundle bundle;

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
    @Inject
    PublisherJpaController publisherController;

    private List<Book> bk;
    private List<Review> rev;
    private List<Invoice> inv;
    private List<News> news;
    private List<Integer> approvalStatus;
    private List<String> revStatuses;
    private List<Boolean> newsStatus;
    private List<Publisher> pubs;
    private Book selectedBook;
    private Book newBook;
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
//        revStatuses.add(bundle.getString("pend"));
//        revStatuses.add(bundle.getString("approve"));
        revStatuses.add("pend");
        revStatuses.add("approve");

        newsStatus = new ArrayList<Boolean>();
        newsStatus.add(true);
        newsStatus.add(false);

        approvalStatus = new ArrayList<Integer>();
        newSurvey = new Survey();
        approvalStatus.add(0);
        approvalStatus.add(1);
        newBook = new Book();
        newBook.setTitle("");
        newBook.setIsbn("");
        newBook.setDescription("");
        newBook.setEnteredDate(new Date());
        newBook.setGenre("");
        newBook.setPages(0);
        newBook.setRemovalStatusBoolean(false);
        newBook.setFormat(".png");
        newBook.setListPrice(BigDecimal.ZERO);
        newBook.setWholesalePrice(BigDecimal.ZERO);
        newBook.setSalePrice(BigDecimal.ZERO);
        newBook.setPublishDate(null);

        pubs = publisherController.findPublisherEntities();

//        context = FacesContext.getCurrentInstance();
//        bundle = context.getApplication().getResourceBundle(context, "msg");
    }

    public List<Review> getRev() {
        return rev;
    }

    public void setRev(List<Review> rev) {
        this.rev = rev;
    }

//  public void onRowAdd(RowEditEvent event) throws Exception {
//    Book newBook = (Book) event.getObject();
//    bkcon.create(newBook);
//    FacesMessage msg = new FacesMessage("new Book");
//    FacesContext.getCurrentInstance().addMessage(null, msg);
//  }
    public void onRowEdit(RowEditEvent event) throws Exception {
        context = FacesContext.getCurrentInstance();
        bundle = context.getApplication().getResourceBundle(context, "msgs");
        String item = "";
        String editType = "";
        if (event.getObject() instanceof Book) {
            onBookRowEdit(event);
            item = bundle.getString("Book") + " " + ((Book) event.getObject()).getIsbn();
            editType = bundle.getString("editBk");
        } else if (event.getObject() instanceof Review) {
            onReviewRowEdit(event);
            item = bundle.getString("review") + " #" + ((Review) event.getObject()).getId().toString();
            editType = bundle.getString("editRev");
        } else if (event.getObject() instanceof Invoice) {
            onInvoiceRowEdit(event);
            item = bundle.getString("invoice") + ((Invoice) event.getObject()).getId();
            editType = bundle.getString("editInv");
        } else if (event.getObject() instanceof InvoiceDetails) {
            onInvDetailRowEdit(event);
            item = bundle.getString("invoiceDetail") + " #" + ((InvoiceDetails) event.getObject()).getId();
            editType = bundle.getString("editInvDetails");
        } else if (event.getObject() instanceof Client) {
            editClient((Client) event.getObject());
            item = bundle.getString("clnt") + " " + bundle.getString("id") + ((Client) event.getObject()).getId();
            editType = bundle.getString("editClnt");;
        } else if (event.getObject() instanceof Survey) {
            editSurvey((Survey) event.getObject());
            item = bundle.getString("survey") + " " + bundle.getString("id") + ((Survey) event.getObject()).getId();
            editType = bundle.getString("editSurvey");
        } else if (event.getObject() instanceof News) {
            onNewsRowEdit(event);
            item = bundle.getString("news") + " " + bundle.getString("id") + ((News) event.getObject()).getId();
            editType = bundle.getString("editNews");
        }

        FacesMessage msg = new FacesMessage(editType, item);
        context.getCurrentInstance().addMessage(null, msg);
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

    private void onNewsRowEdit(RowEditEvent event) throws Exception {
        News newsLink = (News) event.getObject();
        newsCon.edit(newsLink);
    }

    public void onRowCancel(RowEditEvent event) throws Exception {
        context = FacesContext.getCurrentInstance();
        bundle = context.getApplication().getResourceBundle(context, "msgs");
        String item = "";
        if (event.getObject() instanceof Book) {
            item = bundle.getString("book") + " " + ((Book) event.getObject()).getIsbn();
        } else if (event.getObject() instanceof Review) {
            item = bundle.getString("review") + " #" + ((Review) event.getObject()).getId().toString();
        } else if (event.getObject() instanceof Invoice) {
            item = bundle.getString("invoice") + ((Invoice) event.getObject()).getId();
        } else if (event.getObject() instanceof InvoiceDetails) {
            item = bundle.getString("invoiceDetail") + " #" + ((InvoiceDetails) event.getObject()).getId();
        } else if (event.getObject() instanceof Client) {
            item = bundle.getString("clnt") + " " + bundle.getString("id") + ((Client) event.getObject()).getId();
        } else if (event.getObject() instanceof Survey) {
            item = bundle.getString("survey") + " " + bundle.getString("id") + ((Survey) event.getObject()).getId();
        } else if (event.getObject() instanceof News) {
            item = bundle.getString("news") + " " + bundle.getString("id") + ((News) event.getObject()).getId();
        }

        FacesMessage msg = new FacesMessage(bundle.getString("editCancel"), item);
        context.getCurrentInstance().addMessage(null, msg);
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

    public List<Publisher> getPubs() {
        return pubs;
    }

    public void setPubs(List<Publisher> pubs) {
        this.pubs = pubs;
    }

    public Book getNewBook() {
        return newBook;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }

    public void deleteItem() throws Exception {
        bkcon.destroy(((Book) selectedBook).getIsbn());
        selectedBook = null;
    }

    public String checkStat(Integer stat) {
        if (stat.equals(1)) {
            return bundle.getString("avail");
        }

        return bundle.getString("unavail");
    }

    /**
     * client management
     */
    private void editClient(Client c) throws Exception {

        clientJpaController.edit(c);
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

    public void addBook() throws Exception {
        logger.info("New Book is " + newBook);
//        logger.info(newBook.getTitle());
//        logger.info(newBook.getPublisherId().getName());
        bkcon.create(newBook);
//        newBook.setIsbn(null);
//        newBook.setTitle(null);
//        newBook.setPages(0);
//        newBook.setGenre(null);
//        newBook.setPublishDate(null);
//        newBook.setWholesalePrice(BigDecimal.ZERO);
//        newBook.setListPrice(BigDecimal.ZERO);
//        newBook.setSalePrice(BigDecimal.ZERO);
//        newBook.setEnteredDate(new Date());
//        logger.info("New Book Added");
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

    public void handleDialogClose(CloseEvent event) {
        logger.info("new book object");
//        newBook = new Book();

        newBook.setIsbn(null);
        newBook.setTitle(null);
        newBook.setPages(0);
        newBook.setGenre(null);
        newBook.setPublishDate(null);
        newBook.setWholesalePrice(BigDecimal.ZERO);
        newBook.setListPrice(BigDecimal.ZERO);
        newBook.setSalePrice(BigDecimal.ZERO);
        newBook.setEnteredDate(new Date());
    }

    public void updateBook(Book b) throws Exception {
        bkcon.edit(b);
        logger.info("UPDATED BOOK");
    }

    public void resetActive(Integer id) throws Exception {
        for (News ns : news) {
            if (!ns.getId().equals(id)) {
                ns.setActiveStatus(false);
                newsCon.edit(ns);
            }
        }
        news = newsCon.findNewsEntities();
    }

    private void createNewBook() {
//        this.newBook = new Book();
        newBook.setTitle("");
        newBook.setIsbn("");
        newBook.setDescription("");
        newBook.setEnteredDate(new Date());
        newBook.setGenre("");
        newBook.setPages(0);
        newBook.setListPrice(BigDecimal.ZERO);
        newBook.setWholesalePrice(BigDecimal.ZERO);
        newBook.setSalePrice(BigDecimal.ZERO);
        newBook.setPublishDate(null);

    }

}
