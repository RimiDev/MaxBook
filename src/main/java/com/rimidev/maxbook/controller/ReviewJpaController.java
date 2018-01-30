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
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Review;
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
public class ReviewJpaController implements Serializable {

   @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public void create(Review review) throws RollbackFailureException, Exception {
        
        try {
            utx.begin();
            Book isbn = review.getIsbn();
            if (isbn != null) {
                isbn = em.getReference(isbn.getClass(), isbn.getIsbn());
                review.setIsbn(isbn);
            }
            Client clientId = review.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                review.setClientId(clientId);
            }
            em.persist(review);
            if (isbn != null) {
                isbn.getReviewList().add(review);
                isbn = em.merge(isbn);
            }
            if (clientId != null) {
                clientId.getReviewList().add(review);
                clientId = em.merge(clientId);
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

    public void edit(Review review) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
            Review persistentReview = em.find(Review.class, review.getId());
            Book isbnOld = persistentReview.getIsbn();
            Book isbnNew = review.getIsbn();
            Client clientIdOld = persistentReview.getClientId();
            Client clientIdNew = review.getClientId();
            if (isbnNew != null) {
                isbnNew = em.getReference(isbnNew.getClass(), isbnNew.getIsbn());
                review.setIsbn(isbnNew);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                review.setClientId(clientIdNew);
            }
            review = em.merge(review);
            if (isbnOld != null && !isbnOld.equals(isbnNew)) {
                isbnOld.getReviewList().remove(review);
                isbnOld = em.merge(isbnOld);
            }
            if (isbnNew != null && !isbnNew.equals(isbnOld)) {
                isbnNew.getReviewList().add(review);
                isbnNew = em.merge(isbnNew);
            }
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getReviewList().remove(review);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getReviewList().add(review);
                clientIdNew = em.merge(clientIdNew);
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
                Integer id = review.getId();
                if (findReview(id) == null) {
                    throw new NonexistentEntityException("The review with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
            Review review;
            try {
                review = em.getReference(Review.class, id);
                review.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The review with id " + id + " no longer exists.", enfe);
            }
            Book isbn = review.getIsbn();
            if (isbn != null) {
                isbn.getReviewList().remove(review);
                isbn = em.merge(isbn);
            }
            Client clientId = review.getClientId();
            if (clientId != null) {
                clientId.getReviewList().remove(review);
                clientId = em.merge(clientId);
            }
            em.remove(review);
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

    public List<Review> findReviewEntities() {
        return findReviewEntities(true, -1, -1);
    }

    public List<Review> findReviewEntities(int maxResults, int firstResult) {
        return findReviewEntities(false, maxResults, firstResult);
    }

    private List<Review> findReviewEntities(boolean all, int maxResults, int firstResult) {

            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Review.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
       
    }

    public Review findReview(Integer id) {
            return em.find(Review.class, id);
    }

    public int getReviewCount() {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Review> rt = cq.from(Review.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
    }
    
}
