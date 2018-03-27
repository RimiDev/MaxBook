/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
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

  private List<InvoiceDetails> totalSalesList;
  private Date fromDate;
  private double totalSales;

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
    fromDate = new Date();
  }

  public List<InvoiceDetails> getTotalSalesList() {
    List<Invoice> list = invcon.findInvoiceEntities();
    if (list != null) {
      for (Invoice invoice : list) {
        totalSalesList.addAll(invoice.getInvoiceDetailsList());
      }
    }

    return totalSalesList.stream().distinct().collect(Collectors.toList());
  }

  public void setTotalSalesList(List<InvoiceDetails> totalSales) {
    this.totalSalesList = totalSales;
  }

  public void updateFromDate(Date d) {
    this.fromDate = d;
  }
}
