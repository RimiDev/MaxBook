/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.controller;

import com.rimidev.maxbook.controller.exceptions.IllegalOrphanException;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.PreexistingEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Taxes;
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
public class TaxesJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public void create(Taxes taxes) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (taxes.getClientList() == null) {
            taxes.setClientList(new ArrayList<Client>());
        }
        
        try {
            utx.begin();         List<Client> attachedClientList = new ArrayList<Client>();
            for (Client clientListClientToAttach : taxes.getClientList()) {
                clientListClientToAttach = em.getReference(clientListClientToAttach.getClass(), clientListClientToAttach.getId());
                attachedClientList.add(clientListClientToAttach);
            }
            taxes.setClientList(attachedClientList);
            em.persist(taxes);
            for (Client clientListClient : taxes.getClientList()) {
                Taxes oldProvinceOfClientListClient = clientListClient.getProvince();
                clientListClient.setProvince(taxes);
                clientListClient = em.merge(clientListClient);
                if (oldProvinceOfClientListClient != null) {
                    oldProvinceOfClientListClient.getClientList().remove(clientListClient);
                    oldProvinceOfClientListClient = em.merge(oldProvinceOfClientListClient);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTaxes(taxes.getProvince()) != null) {
                throw new PreexistingEntityException("Taxes " + taxes + " already exists.", ex);
            }
            throw ex;
        } 
    }

    public void edit(Taxes taxes) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();         Taxes persistentTaxes = em.find(Taxes.class, taxes.getProvince());
            List<Client> clientListOld = persistentTaxes.getClientList();
            List<Client> clientListNew = taxes.getClientList();
            List<String> illegalOrphanMessages = null;
            for (Client clientListOldClient : clientListOld) {
                if (!clientListNew.contains(clientListOldClient)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Client " + clientListOldClient + " since its province field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Client> attachedClientListNew = new ArrayList<Client>();
            for (Client clientListNewClientToAttach : clientListNew) {
                clientListNewClientToAttach = em.getReference(clientListNewClientToAttach.getClass(), clientListNewClientToAttach.getId());
                attachedClientListNew.add(clientListNewClientToAttach);
            }
            clientListNew = attachedClientListNew;
            taxes.setClientList(clientListNew);
            taxes = em.merge(taxes);
            for (Client clientListNewClient : clientListNew) {
                if (!clientListOld.contains(clientListNewClient)) {
                    Taxes oldProvinceOfClientListNewClient = clientListNewClient.getProvince();
                    clientListNewClient.setProvince(taxes);
                    clientListNewClient = em.merge(clientListNewClient);
                    if (oldProvinceOfClientListNewClient != null && !oldProvinceOfClientListNewClient.equals(taxes)) {
                        oldProvinceOfClientListNewClient.getClientList().remove(clientListNewClient);
                        oldProvinceOfClientListNewClient = em.merge(oldProvinceOfClientListNewClient);
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
                String id = taxes.getProvince();
                if (findTaxes(id) == null) {
                    throw new NonexistentEntityException("The taxes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
            Taxes taxes;
            try {
                taxes = em.getReference(Taxes.class, id);
                taxes.getProvince();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The taxes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Client> clientListOrphanCheck = taxes.getClientList();
            for (Client clientListOrphanCheckClient : clientListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Taxes (" + taxes + ") cannot be destroyed since the Client " + clientListOrphanCheckClient + " in its clientList field has a non-nullable province field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(taxes);
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

    public List<Taxes> findTaxesEntities() {
        return findTaxesEntities(true, -1, -1);
    }

    public List<Taxes> findTaxesEntities(int maxResults, int firstResult) {
        return findTaxesEntities(false, maxResults, firstResult);
    }

    private List<Taxes> findTaxesEntities(boolean all, int maxResults, int firstResult) {

            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Taxes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
      
    }

    public Taxes findTaxes(String id) {

            return em.find(Taxes.class, id);
      
    }

    public int getTaxesCount() {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Taxes> rt = cq.from(Taxes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
    }
    
}
