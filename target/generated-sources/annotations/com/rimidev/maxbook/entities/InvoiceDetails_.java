package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Invoice;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-27T14:27:16")
@StaticMetamodel(InvoiceDetails.class)
public class InvoiceDetails_ { 

    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> pSTrate;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> gSTrate;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> hSTrate;
    public static volatile SingularAttribute<InvoiceDetails, Boolean> removalStatus;
    public static volatile SingularAttribute<InvoiceDetails, Book> isbn;
    public static volatile SingularAttribute<InvoiceDetails, Invoice> invoiceId;
    public static volatile SingularAttribute<InvoiceDetails, Integer> id;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> bookPrice;

}