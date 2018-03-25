package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Author;
import com.rimidev.maxbook.entities.InvoiceDetails;
import com.rimidev.maxbook.entities.Publisher;
import com.rimidev.maxbook.entities.Review;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-25T14:40:51")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile ListAttribute<Book, Review> reviewList;
    public static volatile SingularAttribute<Book, BigDecimal> salePrice;
    public static volatile SingularAttribute<Book, Date> enteredDate;
    public static volatile SingularAttribute<Book, Character> removalStatus;
    public static volatile SingularAttribute<Book, String> isbn;
    public static volatile SingularAttribute<Book, Date> publishDate;
    public static volatile SingularAttribute<Book, String> format;
    public static volatile SingularAttribute<Book, String> description;
    public static volatile SingularAttribute<Book, String> title;
    public static volatile SingularAttribute<Book, Publisher> publisherId;
    public static volatile SingularAttribute<Book, Integer> pages;
    public static volatile ListAttribute<Book, Author> authorList;
    public static volatile SingularAttribute<Book, String> genre;
    public static volatile ListAttribute<Book, InvoiceDetails> invoiceDetailsList;
    public static volatile SingularAttribute<Book, BigDecimal> wholesalePrice;
    public static volatile SingularAttribute<Book, BigDecimal> listPrice;

}