package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.controller.InvoiceJpaController;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Philippe Langlois-Pedroso, 1542705
 */
@Named("invoiceBacking")
@SessionScoped
public class InvoicePageBackingBean implements Serializable{
    
    @Inject
    private InvoiceJpaController invoiceController;
    @Inject
    private InvoiceDetailsJpaController invoiceDetailsController;
    @Inject
    private ClientJpaController clientController;
    
    private Invoice invoice;
    private InvoiceDetails details;
    private static final Logger logger = Logger.getLogger(InvoicePageBackingBean.class.getName());
    
    public Invoice getInvoice() throws Exception{
        logger.info("getInvoice()");
        if(invoice == null){
            invoice = new Invoice();
            invoice.setId(invoiceController.getInvoiceCount() + 1);
            invoice.setDateOfSale(Date.valueOf(LocalDate.now()));
            invoice.setClientId(clientController.findClient(1));
            invoice.setGrossValue(new BigDecimal(1300.00));
            invoice.setNetValue(new BigDecimal(1500.00));
            details = invoiceDetailsController.findInvoiceDetails(1);
        }
        return this.invoice;
    }
    
    public InvoiceDetails getDetails(){
        return details;
    }
    
    public int getInvoiceId(){
        return invoice.getId();
    }
    
    public java.util.Date getDateOfSale(){
        return invoice.getDateOfSale();
    }
    
    public Client getClient(){
        return invoice.getClientId();
    }
    
    public BigDecimal getGross(){
        return invoice.getGrossValue();
    }
    
    public BigDecimal getNet(){
        return invoice.getNetValue();
    }
    
    public String getLocation(){
        String d = invoice.getClientId().getCity();
        d += ", " +invoice.getClientId().getProvince().getProvince();
        d += ", " +invoice.getClientId().getCountry();
        return d;
    }
}
