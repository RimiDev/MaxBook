package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-31T14:51:17")
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