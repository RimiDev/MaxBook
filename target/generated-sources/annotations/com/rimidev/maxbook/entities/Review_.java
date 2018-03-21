package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T18:20:08")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T19:46:26")
>>>>>>> 6d1b65f471e0c4ae210eeebf945823f1dcb3c95b
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