package com.rimidev.backing;

import com.rimidev.maxbook.controller.BookJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author eric Hughes
 * @author Philippe Langlois-Pedroso, 1542705
 * @author Rhai Hinds
 */
@Named
@SessionScoped
public class ReportsBackingBean implements Serializable {

  private Logger logger = Logger.getLogger(ReportsBackingBean.class.getName());
  @Inject
  InvoiceJpaController invcon;

  @Inject
  InvoiceDetailsJpaController invoiceDetailsJpaController;

  @Inject
  BookJpaController bookJpaController;

  private Date fromDate;
  //private double totalSalesValue;
  private List<Invoice> invoicesByClient;
  private List<InvoiceDetails> invoiceDetailsByClient;
  private List<Object[]> invoicesByAuthor;
  private List<Object[]> invoicesByPublisher;
  private List<Invoice> totalInvoiceSales;
  private List<Object[]> topSellers;
  private List<Object[]> topClients;
  private List<InvoiceDetails> totalInvoiceDetails;
  private List<Object[]> filteredClients;
  private List<Object[]> filteredAuthors;

  private List<Object[]> filteredPublishers;

  public int getCountOfISBNSold(String isbn) {

    return invoiceDetailsJpaController.getTotalSold(isbn, fromDate, toDate);
  }
  
  public List<Object[]> getTopSellers() {
    return topSellers;
  }

  public void setTopSellers(List<Object[]> list) {
    topSellers = list;
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
    Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    cal.add(Calendar.YEAR, -1); // to get previous year add -1
    Date lastYear = cal.getTime();
    fromDate = lastYear;
    toDate = new Date();
    totalInvoiceDetails = new ArrayList();
    invoicesByClient = new ArrayList();
    invoiceDetailsByClient = new ArrayList();
    invoicesByAuthor = new ArrayList();
    invoicesByPublisher = new ArrayList();
//    filteredClients = new ArrayList();
//    filteredAuthors = new ArrayList();
//    filteredPublishers = new ArrayList();
  }

  public List<Invoice> getTotalInvoiceSales() {
    totalInvoiceSales = invoiceDetailsJpaController.getTotalInvoiceSales(fromDate, toDate);
    this.getTotalInvoiceSalesValue();
    return totalInvoiceSales;
  }

  public List<InvoiceDetails> getTotalInvoiceDetailSales() {
    totalInvoiceDetails = invoiceDetailsJpaController.getTotalInvoiceDetailSales(fromDate, toDate);
    
    return totalInvoiceDetails;
  }



  public List<Invoice> getTotalInvoicesByClient() {
    invoicesByClient = invoiceDetailsJpaController.getTotalInvoicesByClient(fromDate, toDate);
    this.getTotalInvoiceSalesByClientValue();
    this.getTotalInvoiceSalesByAuthorValue();
    return invoicesByClient;
  }

  public List<InvoiceDetails> getTotalInvoiceDetailsByClient() {
    invoiceDetailsByClient = invoiceDetailsJpaController.getTotalInvoicesDetailsByClient(fromDate, toDate);
    return invoiceDetailsByClient;
  }

  public List<Object[]> getTotalInvoiceDetailsByAuthor() {
    invoicesByAuthor = invoiceDetailsJpaController.getTotalInvoiceDetailsByAuthor(fromDate, toDate);
    return invoicesByAuthor;
  }


  public List<Book> getZeroSales() {
    List<Book> allBooks = bookJpaController.findBookEntities();
    List<InvoiceDetails> details = invoiceDetailsJpaController.getTotalInvoiceDetailSales(fromDate, toDate);
    for (InvoiceDetails d : details) {
      if (allBooks.contains(d.getIsbn())) {
        allBooks.remove(d.getIsbn());
      }
    }
    return allBooks;
  }
  
   public double getTotalInvoiceSalesValue() {
    double totalSalesValue = 0;
    if (totalInvoiceSales != null) {
      for (Invoice sale : totalInvoiceSales) {

        totalSalesValue += sale.getGrossValue().doubleValue();
      }
      logger.log(Level.SEVERE, "getTotalInvoiceSalesValue" + totalSalesValue);
    }
    return totalSalesValue;

  }

  public double getTotalInvoiceSalesByClientValue() {
    double totalSalesValue = 0;
    if (invoicesByClient != null) {
      for (Invoice sale : invoicesByClient) {
        logger.log(Level.SEVERE, "getTotalInvoiceSalesByClientValue " + totalSalesValue);
        totalSalesValue += sale.getGrossValue().doubleValue();
      }
     
    }
    return totalSalesValue;

  }

  public double getTotalInvoiceSalesByAuthorValue() {
    double totalSalesValue = 0;
    if (invoicesByAuthor != null) {
      for (Object[] sale : invoicesByAuthor) {

        Invoice i = (Invoice) sale[0];
        totalSalesValue += i.getGrossValue().doubleValue();
      }
      logger.log(Level.SEVERE, "getTotalInvoiceSalesByAuthorValue" + totalSalesValue);
    }
    return totalSalesValue;

  }

  public double getTotalInvoiceSalesByPublisherValue() {
    double totalSalesValue = 0;
    if (invoicesByPublisher != null) {
      for (Object[] sale : invoicesByPublisher) {
        Invoice i = (Invoice) sale[1];
        totalSalesValue += i.getGrossValue().doubleValue();
      }
      logger.log(Level.SEVERE, "total invoice sales value" + totalSalesValue);
    }
    return totalSalesValue;

  }
  
   public void setTotalSalesList(List<InvoiceDetails> totalSales) {
    this.totalInvoiceDetails = totalSales;
  }

  public void updateTotalInvoicesTable(SelectEvent event) {
    this.getTotalInvoiceSales();
  }

  public void updateTotalInvoiceDetailsTable(SelectEvent event) {
    this.getTotalInvoiceDetailSales();
  }

  public void updateTotalSalesByClientTable(SelectEvent event) {
    this.getTotalInvoicesByClient();
  }

  public void updateTotalSalesDetailsTable(SelectEvent event) {
    this.getTotalInvoiceDetailSales();
  }

 public void updateTopSellersTable(SelectEvent event) {
    this.getTopSellers();
  }
  
  public void updateTopClientsTable(SelectEvent event) {
    this.getTopClients();
  }

  public List<Object[]> getTopSellingBooks() {
    topSellers = invoiceDetailsJpaController.getTopSellers(fromDate, toDate);
    return topSellers;
  }

  public List<Object[]> getTopClients() {
    topClients = invoiceDetailsJpaController.getTopClients(fromDate, toDate);
    return topClients;
            
  }

}
