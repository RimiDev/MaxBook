package com.rimidev.maxbook.controller;

import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Ads;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author maximelacasse, Philippe Langlois-Pedroso
 */
@Named
@RequestScoped
public class AdsJpaController implements Serializable {

    private Logger log = Logger.getLogger(AdsJpaController.class.getName());

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public void create(Ads ads) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(ads);
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

    public void edit(Ads ads) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            ads = em.merge(ads);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ads.getId();
                if (findAds(id) == null) {
                    throw new NonexistentEntityException("The ads with id " + id + " no longer exists.");
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
            Ads ads;
            try {
                ads = em.getReference(Ads.class, id);
                ads.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ads with id " + id + " no longer exists.", enfe);
            }
            em.remove(ads);
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

    public List<Ads> findAdsEntities() {
        return findAdsEntities(true, -1, -1);
    }

    public List<Ads> findAdsEntities(int maxResults, int firstResult) {
        return findAdsEntities(false, maxResults, firstResult);
    }

    private List<Ads> findAdsEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ads.class));
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

    public Ads findAds(Integer id) {
        try {
            return em.find(Ads.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdsCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ads> rt = cq.from(Ads.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //Custom Queries
    public List<Ads> getAllAds() {
        Query q = em.createNamedQuery("Ads.findByActive");
        q.setParameter("active", "1");
        return (List<Ads>) q.getResultList();
    }

    public List<Ads> getFrontAds() {
        List<Ads> front = getAllAds();
        return front.subList(0, (front.size() / 2));
    }

    public List<Ads> getBackAds() {
        List<Ads> back = getAllAds();
        return back.subList((back.size() / 2), back.size());
    }
}
