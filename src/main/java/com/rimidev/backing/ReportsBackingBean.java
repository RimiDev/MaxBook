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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

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

  private Date fromDate;
  //private double totalSalesValue;
  private List<Object[]> clients;
  private List<Object[]> authors;
  private List<Object[]> publishers;
  private List<Invoice> totalInvoiceSales;

  private List<InvoiceDetails> totalSalesList;
  private List<Object[]> filteredClients;
  private List<Object[]> filteredAuthors;

  private List<Object[]> filteredPublishers;

//  public double getTotalSales() {
//    double totalSalesValue=0;
//    if (invoicesList != null) {
//      for (Invoice sale : invoicesList) {
//
//        totalSalesValue += sale.getGrossValue().doubleValue();
//      }
//      logger.log(Level.SEVERE, "total sales" + totalSalesValue);
//    }
//    return totalSalesValue;
//  }
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
    Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    cal.add(Calendar.YEAR, -1); // to get previous year add -1
    Date lastYear = cal.getTime();
    fromDate = lastYear;
    toDate = new Date();
    totalSalesList = new ArrayList();
    clients = new ArrayList();
    authors = new ArrayList();
    publishers = new ArrayList();
    filteredClients = new ArrayList();
    filteredAuthors = new ArrayList();
    filteredPublishers = new ArrayList();
  }
  
  public List<Invoice> getTotalInvoiceSales() {
    totalInvoiceSales = invoiceDetailsJpaController.getTotalInvoiceSales(fromDate, toDate);

    return totalInvoiceSales;
  }

  public List<InvoiceDetails> getTotalInvoiceDetailSales() {
    totalSalesList = invoiceDetailsJpaController.getTotalInvoiceDetailSales(fromDate, toDate);

    return totalSalesList;
  }

  public double getTotalSalesValue() {
    double totalSalesValue = 0;
    totalInvoiceSales = invcon.findInvoiceEntities();
    if (totalInvoiceSales != null) {
      for (Invoice sale : totalInvoiceSales) {

        totalSalesValue += sale.getGrossValue().doubleValue();
      }
      logger.log(Level.SEVERE, "total invoice sales value" + totalSalesValue);
    }
    return totalSalesValue;

  }

  public List<Object[]> getSalesByClient() {
    return clients = invoiceDetailsJpaController.getTotalSalesByClient(new Date(), new Date());
  }

  public List<Object[]> getSalesByAuthor() {
    return authors = invoiceDetailsJpaController.getTotalSalesByAuthor(new Date(), new Date());
  }

  /**
   *
   * @return
   */
  public List<Object[]> getSalesByPublisher() {
    return clients = invoiceDetailsJpaController.getTotalSalesByPublisher(new Date(), new Date());
  }

  public void setTotalSalesList(List<InvoiceDetails> totalSales) {
    this.totalSalesList = totalSales;
  }

  public void updateTotalInvoiceDetailsTable(SelectEvent event) {
    this.getTotalInvoiceDetailSales();
  }
  
  public void updateTotalInvoicesTable(SelectEvent event) {
    this.getTotalInvoiceSales();
  }

  public void updateTotalInvoiceDetailsTable(SelectEvent event) {
    this.getTotalInvoiceDetailSales();
  }

  public void setFilteredPublishers(List<Object[]> filteredPublihsers) {
    this.filteredPublishers = filteredPublihsers;
  }

  public List<Invoice> getInvoicesList() {
    return totalInvoiceSales;
  }

}
