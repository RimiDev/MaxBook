package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T15:34:53")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-13T17:00:32")
>>>>>>> 53f50317d7588c62e2c299abb9b4d6c7f0bf970a
@StaticMetamodel(Publisher.class)
public class Publisher_ { 

    public static volatile SingularAttribute<Publisher, String> name;
    public static volatile SingularAttribute<Publisher, Integer> id;
    public static volatile ListAttribute<Publisher, Book> bookList;

}