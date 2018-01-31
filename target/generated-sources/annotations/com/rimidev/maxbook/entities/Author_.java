package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Book;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-31T14:51:17")
@StaticMetamodel(Author.class)
public class Author_ { 

    public static volatile SingularAttribute<Author, String> firstName;
    public static volatile SingularAttribute<Author, String> lastName;
    public static volatile SingularAttribute<Author, Integer> id;
    public static volatile ListAttribute<Author, Book> bookList;

}