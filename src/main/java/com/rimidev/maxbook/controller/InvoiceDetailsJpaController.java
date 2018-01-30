/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.controller;

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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1513733
 */
public class InvoiceDetailsJpaController implements Serializable {

    public InvoiceDetailsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InvoiceDetails invoiceDetails) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InvoiceDetails invoiceDetails) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InvoiceDetails> findInvoiceDetailsEntities() {
        return findInvoiceDetailsEntities(true, -1, -1);
    }

    public List<InvoiceDetails> findInvoiceDetailsEntities(int maxResults, int firstResult) {
        return findInvoiceDetailsEntities(false, maxResults, firstResult);
    }

    private List<InvoiceDetails> findInvoiceDetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InvoiceDetails.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public InvoiceDetails findInvoiceDetails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InvoiceDetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoiceDetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InvoiceDetails> rt = cq.from(InvoiceDetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
