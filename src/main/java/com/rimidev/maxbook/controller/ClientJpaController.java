/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.controller;

import com.rimidev.maxbook.controller.exceptions.IllegalOrphanException;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Client;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rimidev.maxbook.entities.Taxes;
import com.rimidev.maxbook.entities.Review;
import java.util.ArrayList;
import java.util.List;
import com.rimidev.maxbook.entities.Invoice;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1513733
 */
@Named
@RequestScoped
public class ClientJpaController implements Serializable {

     @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
    public void create(Client client) throws RollbackFailureException, Exception {
        if (client.getReviewList() == null) {
            client.setReviewList(new ArrayList<Review>());
        }
        if (client.getInvoiceList() == null) {
            client.setInvoiceList(new ArrayList<Invoice>());
        }
        
        try {
            utx.begin();
            Taxes province = client.getProvince();
            if (province != null) {
                province = em.getReference(province.getClass(), province.getProvince());
                client.setProvince(province);
            }
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : client.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getId());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            client.setReviewList(attachedReviewList);
            List<Invoice> attachedInvoiceList = new ArrayList<Invoice>();
            for (Invoice invoiceListInvoiceToAttach : client.getInvoiceList()) {
                invoiceListInvoiceToAttach = em.getReference(invoiceListInvoiceToAttach.getClass(), invoiceListInvoiceToAttach.getId());
                attachedInvoiceList.add(invoiceListInvoiceToAttach);
            }
            client.setInvoiceList(attachedInvoiceList);
            em.persist(client);
            if (province != null) {
                province.getClientList().add(client);
                province = em.merge(province);
            }
            for (Review reviewListReview : client.getReviewList()) {
                Client oldClientIdOfReviewListReview = reviewListReview.getClientId();
                reviewListReview.setClientId(client);
                reviewListReview = em.merge(reviewListReview);
                if (oldClientIdOfReviewListReview != null) {
                    oldClientIdOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldClientIdOfReviewListReview = em.merge(oldClientIdOfReviewListReview);
                }
            }
            for (Invoice invoiceListInvoice : client.getInvoiceList()) {
                Client oldClientIdOfInvoiceListInvoice = invoiceListInvoice.getClientId();
                invoiceListInvoice.setClientId(client);
                invoiceListInvoice = em.merge(invoiceListInvoice);
                if (oldClientIdOfInvoiceListInvoice != null) {
                    oldClientIdOfInvoiceListInvoice.getInvoiceList().remove(invoiceListInvoice);
                    oldClientIdOfInvoiceListInvoice = em.merge(oldClientIdOfInvoiceListInvoice);
                }
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

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
            Client persistentClient = em.find(Client.class, client.getId());
            Taxes provinceOld = persistentClient.getProvince();
            Taxes provinceNew = client.getProvince();
            List<Review> reviewListOld = persistentClient.getReviewList();
            List<Review> reviewListNew = client.getReviewList();
            List<Invoice> invoiceListOld = persistentClient.getInvoiceList();
            List<Invoice> invoiceListNew = client.getInvoiceList();
            List<String> illegalOrphanMessages = null;
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewListOldReview + " since its clientId field is not nullable.");
                }
            }
            for (Invoice invoiceListOldInvoice : invoiceListOld) {
                if (!invoiceListNew.contains(invoiceListOldInvoice)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Invoice " + invoiceListOldInvoice + " since its clientId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (provinceNew != null) {
                provinceNew = em.getReference(provinceNew.getClass(), provinceNew.getProvince());
                client.setProvince(provinceNew);
            }
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getId());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            client.setReviewList(reviewListNew);
            List<Invoice> attachedInvoiceListNew = new ArrayList<Invoice>();
            for (Invoice invoiceListNewInvoiceToAttach : invoiceListNew) {
                invoiceListNewInvoiceToAttach = em.getReference(invoiceListNewInvoiceToAttach.getClass(), invoiceListNewInvoiceToAttach.getId());
                attachedInvoiceListNew.add(invoiceListNewInvoiceToAttach);
            }
            invoiceListNew = attachedInvoiceListNew;
            client.setInvoiceList(invoiceListNew);
            client = em.merge(client);
            if (provinceOld != null && !provinceOld.equals(provinceNew)) {
                provinceOld.getClientList().remove(client);
                provinceOld = em.merge(provinceOld);
            }
            if (provinceNew != null && !provinceNew.equals(provinceOld)) {
                provinceNew.getClientList().add(client);
                provinceNew = em.merge(provinceNew);
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Client oldClientIdOfReviewListNewReview = reviewListNewReview.getClientId();
                    reviewListNewReview.setClientId(client);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldClientIdOfReviewListNewReview != null && !oldClientIdOfReviewListNewReview.equals(client)) {
                        oldClientIdOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldClientIdOfReviewListNewReview = em.merge(oldClientIdOfReviewListNewReview);
                    }
                }
            }
            for (Invoice invoiceListNewInvoice : invoiceListNew) {
                if (!invoiceListOld.contains(invoiceListNewInvoice)) {
                    Client oldClientIdOfInvoiceListNewInvoice = invoiceListNewInvoice.getClientId();
                    invoiceListNewInvoice.setClientId(client);
                    invoiceListNewInvoice = em.merge(invoiceListNewInvoice);
                    if (oldClientIdOfInvoiceListNewInvoice != null && !oldClientIdOfInvoiceListNewInvoice.equals(client)) {
                        oldClientIdOfInvoiceListNewInvoice.getInvoiceList().remove(invoiceListNewInvoice);
                        oldClientIdOfInvoiceListNewInvoice = em.merge(oldClientIdOfInvoiceListNewInvoice);
                    }
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
                Integer id = client.getId();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Review> reviewListOrphanCheck = client.getReviewList();
            for (Review reviewListOrphanCheckReview : reviewListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Review " + reviewListOrphanCheckReview + " in its reviewList field has a non-nullable clientId field.");
            }
            List<Invoice> invoiceListOrphanCheck = client.getInvoiceList();
            for (Invoice invoiceListOrphanCheckInvoice : invoiceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Invoice " + invoiceListOrphanCheckInvoice + " in its invoiceList field has a non-nullable clientId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Taxes province = client.getProvince();
            if (province != null) {
                province.getClientList().remove(client);
                province = em.merge(province);
            }
            em.remove(client);
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

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    
    }

    public Client findClient(Integer id) {

            return em.find(Client.class, id);
    }

    public int getClientCount() {

            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
    }
    
//Custom queries-----------------------------------------------------------------
    
    /**
     * Find a record with the specified email
     *
     * @param email
     * @return
     */
    public Client findClientByEmail(String email) {
        TypedQuery<Client> query = em.createNamedQuery("Client.findByEmail", Client.class);
        query.setParameter("email", email);
        List<Client> clients = query.getResultList();
        if (!clients.isEmpty()) {
            return clients.get(0);
        }
        return null;
    }


  

    
}
