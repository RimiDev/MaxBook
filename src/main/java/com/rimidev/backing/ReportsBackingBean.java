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
 *
 *
 * @author eric
 * @author Philippe Langlois-Pedroso
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
  private List<Object[]> publishers;
  private List<Object[]> books;
  private List<Invoice> totalInvoiceSales;

  private List<InvoiceDetails> totalInvoiceDetails;
  private List<Object[]> filteredClients;
  private List<Object[]> filteredAuthors;

  private List<Object[]> filteredPublishers;
  private List<Object[]> filteredBooks;

  public int getCountOfISBNSold(String isbn) {
    
    return invoiceDetailsJpaController.getTotalSold(isbn, fromDate, toDate);
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
    publishers = new ArrayList();
    books = new ArrayList();
    filteredClients = new ArrayList();
    filteredAuthors = new ArrayList();
    filteredPublishers = new ArrayList();
    filteredBooks = new ArrayList();
  }

  public List<Invoice> getTotalInvoiceSales() {
    totalInvoiceSales = invoiceDetailsJpaController.getTotalInvoiceSales(fromDate, toDate);

    return totalInvoiceSales;
  }

  public List<InvoiceDetails> getTotalInvoiceDetailSales() {
    totalInvoiceDetails = invoiceDetailsJpaController.getTotalInvoiceDetailSales(fromDate, toDate);

    return totalInvoiceDetails;
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

  public List<Invoice> getTotalInvoicesByClient() {
    invoicesByClient = invoiceDetailsJpaController.getTotalInvoicesByClient(fromDate, toDate);
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
  
  public List<Book> getZeroSales(){
      List<Book> allBooks = bookJpaController.getAllBooks();
      List<InvoiceDetails> details = invoiceDetailsJpaController.getTotalInvoiceDetailSales(fromDate, toDate);
      for(InvoiceDetails d : details){
          if(allBooks.contains(d.getIsbn())){
              allBooks.remove(d.getIsbn());
          }
      }
      return allBooks;
  }

 

  public void setTotalSalesList(List<InvoiceDetails> totalSales) {
    this.totalInvoiceDetails = totalSales;
  }

  public void updateTotalInvoicesTable(SelectEvent event) {
    this.getTotalInvoiceSales();
  }

  public List<Object[]> getClients() {
    return clients;
  }

  public void setClients(List<Object[]> clients) {
    this.clients = clients;
  }

  public List<Object[]> getAuthors() {
    return authors;
  }

  public void setAuthors(List<Object[]> authors) {
    this.authors = authors;
  }

  public List<Object[]> getPublishers() {
    return publishers;
  }

  public void setPublishers(List<Object[]> publishers) {
    this.publishers = publishers;
  }
  
  public List<Object[]> getBooks(){
      return this.books;
  }
  
  public void setBooks(List<Object[]> books){
      this.books = books;
  }

  public List<Object[]> getFilteredClients() {
    return filteredClients;
  }

  public void setFilteredClients(List<Object[]> filteredList) {
    this.filteredClients = filteredList;
  }

  public List<Object[]> getFilteredAuthors() {
    return filteredAuthors;
  }

  public void setFilteredAuthors(List<Object[]> filteredAuthors) {
    this.filteredAuthors = filteredAuthors;
  }

  public List<Object[]> getFilteredPublishers() {
    return filteredPublishers;
  }

  public void updateTotalSalesByClientTable(SelectEvent event) {
    this.getTotalInvoicesByClient();
  }
  
  public List<Object[]> getFilteredBooks(){
      return filteredBooks;
  }
  
  public void setFilteredBooks(List<Object[]> filteredBooks){
      this.filteredBooks = filteredBooks;
  }

  public void updateTotalSalesDetailsTable(SelectEvent event) {
    this.getTotalInvoiceDetailSales();
  }

  public void setInvoicesList(List<Invoice> invoicesList) {
    this.totalInvoiceSales = invoicesList;
  }
  
//  public List<Book> findZeroSales(){
//      List<Book> allBooks = bookJpaController.getAllBooks();
//      List<InvoiceDetails> details = invoiceDetailsJpaController.getTotalInvoiceDetailSales(fromDate, toDate);
//      for(InvoiceDetails d : details){
//          if(allBooks.contains(d.getIsbn())){
//              allBooks.remove(d.getIsbn());
//          }
//      }
//      return allBooks;
//  }
}
