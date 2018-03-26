package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-26T03:08:04")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-25T14:40:51")
>>>>>>> a7d211f7901e71e82e462fe41c27cf75d84ad32e
@StaticMetamodel(Author.class)
public class Author_ { 

    public static volatile SingularAttribute<Author, String> firstName;
    public static volatile SingularAttribute<Author, String> lastName;
    public static volatile SingularAttribute<Author, Integer> id;
    public static volatile ListAttribute<Author, Book> bookList;

}