/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.controller;

import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Author;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rimidev.maxbook.entities.Book;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1513733
 */
@Named
@RequestScoped
public class AuthorJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public void create(Author author) throws RollbackFailureException, Exception {
        if (author.getBookList() == null) {
            author.setBookList(new ArrayList<Book>());
        }
        
        try {
            utx.begin();
            
            List<Book> attachedBookList = new ArrayList<Book>();
            for (Book bookListBookToAttach : author.getBookList()) {
                bookListBookToAttach = em.getReference(bookListBookToAttach.getClass(), bookListBookToAttach.getIsbn());
                attachedBookList.add(bookListBookToAttach);
            }
            author.setBookList(attachedBookList);
            em.persist(author);
            for (Book bookListBook : author.getBookList()) {
                bookListBook.getAuthorList().add(author);
                bookListBook = em.merge(bookListBook);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } 
    }

    public void edit(Author author) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            
            Author persistentAuthor = em.find(Author.class, author.getId());
            List<Book> bookListOld = persistentAuthor.getBookList();
            List<Book> bookListNew = author.getBookList();
            List<Book> attachedBookListNew = new ArrayList<Book>();
            for (Book bookListNewBookToAttach : bookListNew) {
                bookListNewBookToAttach = em.getReference(bookListNewBookToAttach.getClass(), bookListNewBookToAttach.getIsbn());
                attachedBookListNew.add(bookListNewBookToAttach);
            }
            bookListNew = attachedBookListNew;
            author.setBookList(bookListNew);
            author = em.merge(author);
            for (Book bookListOldBook : bookListOld) {
                if (!bookListNew.contains(bookListOldBook)) {
                    bookListOldBook.getAuthorList().remove(author);
                    bookListOldBook = em.merge(bookListOldBook);
                }
            }
            for (Book bookListNewBook : bookListNew) {
                if (!bookListOld.contains(bookListNewBook)) {
                    bookListNewBook.getAuthorList().add(author);
                    bookListNewBook = em.merge(bookListNewBook);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = author.getId();
                if (findAuthor(id) == null) {
                    throw new NonexistentEntityException("The author with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            
            Author author;
            try {
                author = em.getReference(Author.class, id);
                author.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The author with id " + id + " no longer exists.", enfe);
            }
            List<Book> bookList = author.getBookList();
            for (Book bookListBook : bookList) {
                bookListBook.getAuthorList().remove(author);
                bookListBook = em.merge(bookListBook);
            }
            em.remove(author);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Author> findAuthorEntities() {
        return findAuthorEntities(true, -1, -1);
    }

    public List<Author> findAuthorEntities(int maxResults, int firstResult) {
        return findAuthorEntities(false, maxResults, firstResult);
    }

    private List<Author> findAuthorEntities(boolean all, int maxResults, int firstResult) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Author.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    }

    public Author findAuthor(Integer id) {
            return em.find(Author.class, id);

    }

    public int getAuthorCount() {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Author> rt = cq.from(Author.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
    }
}
