/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.controller;

import com.rimidev.backing.AllBooksBackingBean;
import com.rimidev.maxbook.beans.BestSellingBooksBean;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.InvoiceDetails;
import static java.lang.System.out;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Selection;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1513733
 */
@Named
@RequestScoped
public class InvoiceDetailsJpaController implements Serializable {
  
  @Resource
  private UserTransaction utx;
  
  @PersistenceContext(unitName = "MaxBookPU")
  private EntityManager em;
  
  private static final Logger logger = Logger.getLogger(InvoiceDetailsJpaController.class.getName());

  public List<InvoiceDetails> getAll() {

    // Object oriented criteria builder
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<InvoiceDetails> cq = cb.createQuery(InvoiceDetails.class);
    Root<InvoiceDetails> fish = cq.from(InvoiceDetails.class);
    cq.select(fish);
    TypedQuery<InvoiceDetails> query = em.createQuery(cq);

    // Using a named query from the entity class
    // TypedQuery<Fish> query =  entityManager.createNamedQuery("Fish.findAll", Fish.class);
    // Execute the query
    List<InvoiceDetails> fishies = query.getResultList();
    
    return fishies;
  }
  
  public void create(InvoiceDetails invoiceDetails) throws RollbackFailureException, Exception {
    
    try {
      utx.begin();
      Invoice invoiceId = invoiceDetails.getInvoiceId();
      if (invoiceId != null) {
        invoiceId = em.getReference(invoiceId.getClass(), invoiceId.getId());
        invoiceDetails.setInvoiceId(invoiceId);
      }
      Book isbn = invoiceDetails.getIsbn();
      if (isbn != null) {
        isbn = em.getReference(isbn.getClass(), isbn.getIsbn());
        invoiceDetails.setIsbn(isbn);
      }
      em.persist(invoiceDetails);
      if (invoiceId != null) {
        invoiceId.getInvoiceDetailsList().add(invoiceDetails);
        invoiceId = em.merge(invoiceId);
      }
      if (isbn != null) {
        isbn.getInvoiceDetailsList().add(invoiceDetails);
        isbn = em.merge(isbn);
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
  
  public void edit(InvoiceDetails invoiceDetails) throws NonexistentEntityException, RollbackFailureException, Exception {
    
    try {
      utx.begin();
      InvoiceDetails persistentInvoiceDetails = em.find(InvoiceDetails.class, invoiceDetails.getId());
      Invoice invoiceIdOld = persistentInvoiceDetails.getInvoiceId();
      Invoice invoiceIdNew = invoiceDetails.getInvoiceId();
      Book isbnOld = persistentInvoiceDetails.getIsbn();
      Book isbnNew = invoiceDetails.getIsbn();
      if (invoiceIdNew != null) {
        invoiceIdNew = em.getReference(invoiceIdNew.getClass(), invoiceIdNew.getId());
        invoiceDetails.setInvoiceId(invoiceIdNew);
      }
      if (isbnNew != null) {
        isbnNew = em.getReference(isbnNew.getClass(), isbnNew.getIsbn());
        invoiceDetails.setIsbn(isbnNew);
      }
      invoiceDetails = em.merge(invoiceDetails);
      if (invoiceIdOld != null && !invoiceIdOld.equals(invoiceIdNew)) {
        invoiceIdOld.getInvoiceDetailsList().remove(invoiceDetails);
        invoiceIdOld = em.merge(invoiceIdOld);
      }
      if (invoiceIdNew != null && !invoiceIdNew.equals(invoiceIdOld)) {
        invoiceIdNew.getInvoiceDetailsList().add(invoiceDetails);
        invoiceIdNew = em.merge(invoiceIdNew);
      }
      if (isbnOld != null && !isbnOld.equals(isbnNew)) {
        isbnOld.getInvoiceDetailsList().remove(invoiceDetails);
        isbnOld = em.merge(isbnOld);
      }
      if (isbnNew != null && !isbnNew.equals(isbnOld)) {
        isbnNew.getInvoiceDetailsList().add(invoiceDetails);
        isbnNew = em.merge(isbnNew);
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
        Integer id = invoiceDetails.getId();
        if (findInvoiceDetails(id) == null) {
          throw new NonexistentEntityException("The invoiceDetails with id " + id + " no longer exists.");
        }
      }
      throw ex;
    }
  }
  
  public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
    
    try {
      utx.begin();
      InvoiceDetails invoiceDetails;
      try {
        invoiceDetails = em.getReference(InvoiceDetails.class, id);
        invoiceDetails.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The invoiceDetails with id " + id + " no longer exists.", enfe);
      }
      Invoice invoiceId = invoiceDetails.getInvoiceId();
      if (invoiceId != null) {
        invoiceId.getInvoiceDetailsList().remove(invoiceDetails);
        invoiceId = em.merge(invoiceId);
      }
      Book isbn = invoiceDetails.getIsbn();
      if (isbn != null) {
        isbn.getInvoiceDetailsList().remove(invoiceDetails);
        isbn = em.merge(isbn);
      }
      em.remove(invoiceDetails);
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
  
  public List<InvoiceDetails> findInvoiceDetailsEntities() {
    return findInvoiceDetailsEntities(true, -1, -1);
  }
  
  public List<InvoiceDetails> findInvoiceDetailsEntities(int maxResults, int firstResult) {
    return findInvoiceDetailsEntities(false, maxResults, firstResult);
  }
  
  private List<InvoiceDetails> findInvoiceDetailsEntities(boolean all, int maxResults, int firstResult) {
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(InvoiceDetails.class));
    Query q = em.createQuery(cq);
    if (!all) {
      q.setMaxResults(maxResults);
      q.setFirstResult(firstResult);
    }
    return q.getResultList();
  }
  
  public InvoiceDetails findInvoiceDetails(Integer id) {
    
    return em.find(InvoiceDetails.class, id);
    
  }
  
  public int getInvoiceDetailsCount() {
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    Root<InvoiceDetails> rt = cq.from(InvoiceDetails.class);
    cq.select(em.getCriteriaBuilder().count(rt));
    Query q = em.createQuery(cq);
    return ((Long) q.getSingleResult()).intValue();
  }

  //Custom queries---------------------------------------------
  public List<Book> getTopSellingBooks() {
    
    TypedQuery<Book> query = em.createQuery("SELECT ivd.isbn FROM InvoiceDetails ivd GROUP BY ivd.isbn ORDER BY COUNT(ivd) DESC", Book.class);
    
    Collection<Book> Invoices = query.getResultList();
    return (List<Book>) Invoices;
  }
  
  public List<Book> getRecentSoldBook() {
    
    TypedQuery<Book> query = em.createQuery("SELECT DISTINCT ivd.isbn FROM InvoiceDetails ivd ORDER BY ivd.id DESC", Book.class);
    //SELECT isbn FROM Invoice_Details ORDER BY id DESC LIMIT 5;
    
    Collection<Book> Invoices = query.setMaxResults(5).getResultList();
    
    return (List<Book>) Invoices;
    
  }
  
  public List<Object[]> getTotalSales(Date from, Date to) {
    
    TypedQuery<Object[]> query = em.createQuery("SELECT i, ivd FROM InvoiceDetails ivd inner join ivd.invoiceId i"
            + " group by ivd.isbn", Object[].class);
    //SELECT isbn FROM Invoice_Details ORDER BY id DESC LIMIT 5;
    logger.log(Level.WARNING, "get total sales query>>>>>>>>>>");
    Collection<Object[]> sales = query.getResultList();
    for (Object[] sale : sales) {
      //logger.log(Level.WARNING, "sale >>>>>>>>>>" + sale.getIsbn());
    }
    return (List<Object[]>) sales;
    
  }
  
  public List<Object[]> getTotalSalesByClient(Date date, Date date0) {
    
    TypedQuery<Object[]> query = em.createQuery("SELECT i.dateOfSale, c.email, c.firstName, c.lastName, ivd FROM InvoiceDetails ivd "
            + "inner join ivd.invoiceId i inner join i.clientId c order by i.dateOfSale desc", Object[].class);
    
    logger.log(Level.WARNING, "get total sales query>>>>>>>>>>");
    Collection<Object[]> sales = query.getResultList();
    
    return (List<Object[]>) sales;
  }

  public List<Object[]> getTotalSalesByAuthor(Date date, Date date0) {
    
    TypedQuery<Object[]> query = em.createQuery("SELECT i.dateOfSale, author, ivd FROM InvoiceDetails ivd "
            + "inner join ivd.invoiceId i inner join ivd.isbn b inner join b.authorList author order by i.dateOfSale desc", Object[].class);
    
    
    Collection<Object[]> sales = query.getResultList();
    
    return (List<Object[]>) sales;
  }
    

  public List<Object[]> getTotalSalesByPublisher(Date date, Date date0) {
    
    TypedQuery<Object[]> query = em.createQuery("SELECT i.dateOfSale, publisher, ivd FROM InvoiceDetails ivd "
            + "inner join ivd.invoiceId i inner join ivd.isbn b inner join b.publisherId publisher order by i.dateOfSale desc", Object[].class);
    
    
    Collection<Object[]> sales = query.getResultList();
    
    return (List<Object[]>) sales;
  }
  
}
