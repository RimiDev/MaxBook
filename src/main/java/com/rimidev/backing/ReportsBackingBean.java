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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private FacesContext context;
    private ResourceBundle bundle;

    @Inject
    InvoiceJpaController invcon;

    private List<InvoiceDetails> totalSalesList;
    private Date fromDate;
    private Date toDate;
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

    @PostConstruct
    public void init() {
        totalSalesList = new ArrayList();
        context = FacesContext.getCurrentInstance();
        bundle = context.getApplication().getResourceBundle(context, "msg");
//        fromDate = new Date();
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

    public void checkDateRange(SelectEvent event) {
        logger.log(Level.INFO, "FromDate: " + fromDate);
        logger.log(Level.INFO, "ToDate: " + toDate);

        FacesMessage msg = new FacesMessage("FromDate: ", fromDate == null ? null : fromDate.toString());
        context.getCurrentInstance().addMessage(null, msg);
        msg = new FacesMessage("ToDate: ", (toDate == null ? null : toDate.toString()));
        context.getCurrentInstance().addMessage(null, msg);

        if (fromDate != null && toDate != null) {
            if (fromDate.after(toDate) || toDate.before(fromDate)) {
                msg = new FacesMessage("Date Error","Invalid Range");
                context.getCurrentInstance().addMessage(null, msg);
            } else {
                logger.log(Level.INFO, "dates not null");
                List<InvoiceDetails> filterList = new ArrayList();
                for (InvoiceDetails t : totalSalesList) {
                    if ((t.getInvoiceId().getDateOfSale().equals(fromDate) || fromDate.before(t.getInvoiceId().getDateOfSale())) && (t.getInvoiceId().getDateOfSale().equals(toDate) || toDate.after(t.getInvoiceId().getDateOfSale()))) {
                        logger.log(Level.INFO, "filtered item added");
                        filterList.add(t);
                    }
                }
                totalSalesList = filterList;
            }
        } else {
            logger.log(Level.INFO, "filter skipped");
            totalSalesList = getTotalSalesList();
        }

    }

    public void filterByRange() {

    }

}
