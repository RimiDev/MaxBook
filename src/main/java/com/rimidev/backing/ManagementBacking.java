/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.SurveyJpaController;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
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
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class ManagementBacking implements Serializable {

  private Logger logger = Logger.getLogger(BookDisplayBacking.class.getName());

  @Inject
  BookJpaController bkcon;
  List<Book> bk;

  @Inject
  ClientJpaController clientJpaController;
  List<Client> clients;
  
  @Inject
  SurveyJpaController surveyJpaController;
  List<Survey> surveys;

  List<Integer> status;
  
  Survey newSurvey;
//    private String message;
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void saveMessage() {
//        FacesContext context = FacesContext.getCurrentInstance();
//
//        context.addMessage(null, new FacesMessage("Successful", "Your message: " + message));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
//    }

  public List<Client> getClients() {
    if (clients == null) {
      clients = clientJpaController.findClientEntities();
    }
    return clients;
  }

  public void setClients(List<Client> clients) {
    this.clients = clients;
  }

  public List<Book> getBk() {
    return bk;
  }

  public void setBk(List<Book> bk) {
    this.bk = bk;
  }
  
  public List<Survey> getSurveys(){
      return surveys;
  }
  
  public void setSurveys(List<Survey> surveys){
      this.surveys = surveys;
  }
  
  public Survey getNewSurvey(){
      return this.newSurvey;
  }

  @PostConstruct
  public void init() {
    bk = bkcon.findBookEntities();
    clients = clientJpaController.findClientEntities();
    surveys = surveyJpaController.findSurveyEntities();
    status = new ArrayList<Integer>();
    newSurvey = new Survey();
    status.add(0);
    status.add(1);
  }

  public void onRowAdd(RowEditEvent event) throws Exception {
    Book newBook = (Book) event.getObject();
    bkcon.create(newBook);
    FacesMessage msg = new FacesMessage("Book Created", String.valueOf(newBook));
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onRowEdit(RowEditEvent event) throws Exception {

    if (event.getObject() instanceof Client) {
      editClient((Client) event.getObject());
    } else if (event.getObject() instanceof Book) {
      Book editedBook = (Book) event.getObject();
      bkcon.edit(editedBook);
      FacesMessage msg = new FacesMessage("Book Edited", String.valueOf(editedBook));
      FacesContext.getCurrentInstance().addMessage(null, msg);
    } else if (event.getObject() instanceof Survey){
        editSurvey((Survey) event.getObject());
    }

  }

  public void onRowRemove(RowEditEvent event) {

  }

  public void onRowCancel(RowEditEvent event) {
    FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().toString());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onCellEdit(CellEditEvent event) {
    Object oldValue = event.getOldValue();
    Object newValue = event.getNewValue();

    if (newValue != null && !newValue.equals(oldValue)) {

    }
  }

  public void newLine(ActionEvent actionEvent) {
    this.bk.add(new Book());
    logger.log(Level.WARNING, "<<Book Inventory List: >>" + bk.get(bk.size() - 1));
  }

  public List<Integer> getStatus() {
    return status;
  }

  public String checkStat(Integer stat) {
    if (stat.equals(1)) {
      return "Unavailable";
    }

    return "Available";
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
  public void editSurvey(Survey survey) throws Exception{
      surveyJpaController.edit(survey);
      FacesMessage msg = new FacesMessage("Survey Edited", Integer.toString(survey.getId()));
      FacesContext.getCurrentInstance().addMessage(null, msg);
  }
  
  public void addSurvey() throws Exception{
      logger.info(newSurvey.getQuestion());
      logger.info(newSurvey.getOption1());
      logger.info(newSurvey.getOption2());
      logger.info(newSurvey.getOption3());
      logger.info(newSurvey.getOption4());
      surveyJpaController.create(newSurvey);
      logger.info("New Survey Added");
  }
 
  public void deleteSurvey() throws Exception{
      logger.info("Test Delete Survey");
  }
}
