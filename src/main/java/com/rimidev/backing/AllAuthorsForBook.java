/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import com.rimidev.maxbook.controller.AuthorJpaController;
import com.rimidev.maxbook.entities.Author;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ehugh
 */
@Named
@RequestScoped
public class AllAuthorsForBook implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public List<Author> getAuthors(String isbn) {
        TypedQuery<Author> query = 
                em.createQuery("SELECT a FROM Book b JOIN b.authorList a where b.isbn = :isbn", Author.class);
        List<Author> list = query.getResultList();
        return list;

    }
}
