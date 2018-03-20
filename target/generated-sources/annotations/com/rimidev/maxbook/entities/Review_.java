package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T15:34:53")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-13T17:00:32")
>>>>>>> 53f50317d7588c62e2c299abb9b4d6c7f0bf970a
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