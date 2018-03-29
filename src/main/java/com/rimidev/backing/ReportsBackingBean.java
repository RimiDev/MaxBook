/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Author;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import com.rimidev.maxbook.entities.Publisher;
import com.rimidev.maxbook.entities.Survey;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * manager can request a number of reports on the inventory, orders and clients.
 *
 * all reports accepta date range and any other details report : summary or
 * details, shows total sales, cost and profit.
 *
 *
 *
 * @author eric
 */
@Named
@SessionScoped
public class ReportsBackingBean implements Serializable {

  private Logger logger = Logger.getLogger(ReportsBackingBean.class.getName());
  @Inject
  InvoiceJpaController invcon;

  @Inject
  InvoiceDetailsJpaController invoiceDetailsJpaController;
  private List<InvoiceDetails> totalSalesList;
  private Date fromDate;
  private double totalSales;
  private List<Object[]> clients;
  private List<InvoiceDetails> authors;
  private List<InvoiceDetails> publishers;
  private List<InvoiceDetails> inventory;

  private List<Object[]> filteredClients;

  public double getTotalSales() {
    if (totalSalesList != null) {
      totalSales = totalSalesList.stream().map(InvoiceDetails::getBookPrice).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue();
      logger.log(Level.SEVERE, "total sales" + totalSales);
    }
    return totalSales;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }
  private Date toDate;

  @PostConstruct
  public void init() {
    totalSalesList = new ArrayList();
    clients = new ArrayList();
    authors = new ArrayList();
    publishers = new ArrayList();
    fromDate = new Date();
    filteredClients = new ArrayList();
  }

  public List<InvoiceDetails> getTotalSalesList() {
    totalSalesList = invoiceDetailsJpaController.getTotalSales(new Date(), new Date());

    return totalSalesList;
  }

  public List<InvoiceDetails> getSalesByAuthor(String client) {
    return null;
  }

  public List<InvoiceDetails> getSalesByPublisher() {
    return null;
  }

  public List<Object[]> getSalesByClient() {
    return clients = invoiceDetailsJpaController.getTotalSalesByClient(new Date(), new Date());
  }

  public void setTotalSalesList(List<InvoiceDetails> totalSales) {
    this.totalSalesList = totalSales;
  }

  public void updateFromDate(Date d) {
    this.fromDate = d;
  }

  public List<Object[]> getClients() {
    return clients;
  }

  public void setClients(List<Object[]> clients) {
    this.clients = clients;
  }

  public List<InvoiceDetails> getAuthors() {
    return authors;
  }

  public void setAuthors(List<InvoiceDetails> authors) {
    this.authors = authors;
  }

  public List<InvoiceDetails> getPublishers() {
    return publishers;
  }

  public void setPublishers(List<InvoiceDetails> publishers) {
    this.publishers = publishers;
  }

  public List<Object[]> getFilteredClients() {
    return filteredClients;
  }

  public void setFilteredClients(List<Object[]> filteredList) {
    this.filteredClients = filteredList;
  }
}
