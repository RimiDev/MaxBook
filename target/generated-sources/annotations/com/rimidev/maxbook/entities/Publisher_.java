package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T18:20:08")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T19:46:26")
>>>>>>> 6d1b65f471e0c4ae210eeebf945823f1dcb3c95b
@StaticMetamodel(Publisher.class)
public class Publisher_ { 

    public static volatile SingularAttribute<Publisher, String> name;
    public static volatile SingularAttribute<Publisher, Integer> id;
    public static volatile ListAttribute<Publisher, Book> bookList;

}