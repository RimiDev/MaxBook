package com.rimidev.maxbook.controller;

import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Survey;
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
import java.util.Random;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;

/**
 *
 * @author plang
 */
@Named
@RequestScoped
public class SurveyJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    private Logger logger = Logger.getLogger(ClientJpaController.class.getName());

    @PersistenceContext
    private EntityManager em;

    public void create(Survey survey) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(survey);
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

    public void edit(Survey survey) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            survey = em.merge(survey);        
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = survey.getId();
                if (findSurvey(id) == null) {
                    throw new NonexistentEntityException("The survey with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Survey survey;
            try {
                survey = em.getReference(Survey.class, id);
                survey.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The survey with id " + id + " no longer exists.", enfe);
            }
            em.remove(survey);
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

    public List<Survey> findSurveyEntities() {
        return findSurveyEntities(true, -1, -1);
    }

    public List<Survey> findSurveyEntities(int maxResults, int firstResult) {
        return findSurveyEntities(false, maxResults, firstResult);
    }

    private List<Survey> findSurveyEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Survey.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } catch(Exception e){
        }
        return null;
    }

    public Survey findSurvey(Integer id) {
        try {
            return em.find(Survey.class, id);
        } catch(Exception e){
        }
        return null;
    }

    public int getSurveyCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Survey> rt = cq.from(Survey.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }catch(Exception e){
        }
        return 0;
    }
    
    public List<Survey> getAllQuestions(){
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Survey.class));
        Query q = em.createQuery(cq);
        return q.getResultList();
    }
    
    /**
     * Method that returns a random question from the database.
     * 
     * @return Survey question
     */
    public Survey getSurveyQuestion(){
        Random random = new Random();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // Get the total number of questions in the database
        CriteriaQuery<Survey> cq = cb.createQuery(Survey.class);
        cq.select(cq.from(Survey.class));
        Query q = em.createQuery(cq);
        int numberOfQuestions = q.getResultList().size();
        // Get a random question for the survey in the database
        int questionId = random.nextInt(numberOfQuestions) + 1;
        CriteriaQuery<Survey> cq2 = cb.createQuery(Survey.class);
        Root<Survey> survey = cq2.from(Survey.class);
        cq2.select(survey);
        cq2.where(cb.equal(survey.get("id"), questionId));
        TypedQuery<Survey> query = em.createQuery(cq2);
        Survey s = query.getSingleResult();
        
        return s;
    }
    
    public void submit(Integer myId, String answer){
        Query qUpdate = null;
        Query qCount = null;
        int newCount = 0;
        
        qUpdate = em.createQuery("UPDATE Survey SET count1 = 17");
        
//        switch(answer){
//            case "1": 
//                qCount = em.createQuery("SELECT s.count1 FROM SURVEY s WHERE s.id = :id");
//                qCount.setParameter("id", myId);
//                newCount = (Integer) qCount.getSingleResult();
//                qUpdate = em.createQuery("UPDATE Survey s SET s.count1 = :count WHERE s.id = :id");
//                qUpdate.setParameter("count", newCount);
//                qUpdate.setParameter("id", myId);
//                break;
//            case "2": 
//                qCount = em.createQuery("SELECT s.count2 FROM SURVEY s WHERE s.id = :id");
//                qCount.setParameter("id", myId);
//                newCount = (Integer) qCount.getSingleResult();
//                qUpdate = em.createQuery("UPDATE Survey s SET s.count2 = :count WHERE s.id = :id");
//                qUpdate.setParameter("count", newCount);
//                qUpdate.setParameter("id", myId);
//                break;
//            case "3": 
//                qCount = em.createQuery("SELECT s.count3 FROM SURVEY s WHERE s.id = :id");
//                qCount.setParameter("id", myId);
//                newCount = (Integer) qCount.getSingleResult();
//                qUpdate = em.createQuery("UPDATE Survey s SET s.count3 = :count WHERE s.id = :id");
//                qUpdate.setParameter("count", newCount);
//                qUpdate.setParameter("id", myId);
//                break;
//            case "4": 
//                qCount = em.createQuery("SELECT s.count4 FROM SURVEY s WHERE s.id = :id");
//                qCount.setParameter("id", myId);
//                newCount = (Integer) qCount.getSingleResult();
//                qUpdate = em.createQuery("UPDATE Survey s SET s.count4 = :count WHERE s.id = :id");
//                qUpdate.setParameter("count", newCount);
//                qUpdate.setParameter("id", myId);
//                break;
//        }   
        
        int updated = qUpdate.executeUpdate();
        logger.info("Update Status : " +Integer.toString(updated));
    }
}
