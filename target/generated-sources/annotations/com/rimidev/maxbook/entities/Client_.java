package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.Review;
import com.rimidev.maxbook.entities.Taxes;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-21T18:47:19")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, String> lastName;
    public static volatile SingularAttribute<Client, String> country;
    public static volatile ListAttribute<Client, Review> reviewList;
    public static volatile SingularAttribute<Client, Integer> manager;
    public static volatile SingularAttribute<Client, String> address2;
    public static volatile SingularAttribute<Client, String> city;
    public static volatile SingularAttribute<Client, String> address1;
    public static volatile SingularAttribute<Client, String> companyName;
    public static volatile SingularAttribute<Client, String> postalCode;
    public static volatile SingularAttribute<Client, String> title;
    public static volatile ListAttribute<Client, Invoice> invoiceList;
    public static volatile SingularAttribute<Client, String> firstName;
    public static volatile SingularAttribute<Client, String> password;
    public static volatile SingularAttribute<Client, String> phoneNumber;
    public static volatile SingularAttribute<Client, Taxes> province;
    public static volatile SingularAttribute<Client, Integer> id;
    public static volatile SingularAttribute<Client, String> email;

}