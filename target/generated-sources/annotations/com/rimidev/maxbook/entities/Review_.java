package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-01T11:28:23")
@StaticMetamodel(Review.class)
public class Review_ { 

    public static volatile SingularAttribute<Review, String> approvalStatus;
    public static volatile SingularAttribute<Review, Client> clientId;
    public static volatile SingularAttribute<Review, Date> reviewDate;
    public static volatile SingularAttribute<Review, String> reviewMessage;
    public static volatile SingularAttribute<Review, Book> isbn;
    public static volatile SingularAttribute<Review, Integer> rating;
    public static volatile SingularAttribute<Review, Integer> id;

}