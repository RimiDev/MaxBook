package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-28T10:38:45")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-27T15:19:45")
>>>>>>> b16caab077586dad423b7a90c32b4432baf09d49
@StaticMetamodel(Invoice.class)
public class Invoice_ { 

    public static volatile SingularAttribute<Invoice, Date> dateOfSale;
    public static volatile SingularAttribute<Invoice, Client> clientId;
    public static volatile SingularAttribute<Invoice, BigDecimal> netValue;
    public static volatile SingularAttribute<Invoice, BigDecimal> grossValue;
    public static volatile SingularAttribute<Invoice, Boolean> removalStatus;
    public static volatile ListAttribute<Invoice, InvoiceDetails> invoiceDetailsList;
    public static volatile SingularAttribute<Invoice, Integer> id;

}