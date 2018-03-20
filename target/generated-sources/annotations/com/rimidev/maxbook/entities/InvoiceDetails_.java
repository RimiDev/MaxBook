package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Invoice;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T15:34:53")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-13T17:00:32")
>>>>>>> 53f50317d7588c62e2c299abb9b4d6c7f0bf970a
@StaticMetamodel(InvoiceDetails.class)
public class InvoiceDetails_ { 

    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> pSTrate;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> gSTrate;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> hSTrate;
    public static volatile SingularAttribute<InvoiceDetails, Book> isbn;
    public static volatile SingularAttribute<InvoiceDetails, Invoice> invoiceId;
    public static volatile SingularAttribute<InvoiceDetails, Integer> id;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> bookPrice;

}