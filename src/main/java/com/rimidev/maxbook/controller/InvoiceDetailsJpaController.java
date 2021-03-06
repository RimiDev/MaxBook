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
import javax.persistence.TemporalType;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Selection;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.UserTransaction;
import com.rimidev.maxbook.entities.Client;

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

    public List<Invoice> getTotalInvoiceSales(Date from, Date to) {
        logger.log(Level.WARNING, "incoming from date " + from.toString());
        logger.log(Level.WARNING, "incoming to date " + to.toString());
        TypedQuery<Invoice> query = em.createQuery("SELECT i FROM Invoice i "
                + "where i.dateOfSale BETWEEN :from AND :to order by i.dateOfSale desc", Invoice.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        logger.log(Level.WARNING, "get total sales query>>>>>>>>>>");
        Collection<Invoice> sales = query.getResultList();

        return (List<Invoice>) sales;
    }

    public List<InvoiceDetails> getTotalInvoiceDetailSales(Date from, Date to) {

        logger.log(Level.WARNING, "incoming from date " + from.toString());
        logger.log(Level.WARNING, "incoming to date " + to.toString());
        TypedQuery<InvoiceDetails> query = em.createQuery("SELECT ivd FROM InvoiceDetails ivd "
                + "where ivd.invoiceId.dateOfSale BETWEEN :from AND :to order by ivd.invoiceId.dateOfSale desc", InvoiceDetails.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        logger.log(Level.WARNING, "get total sales query>>>>>>>>>>");
        Collection<InvoiceDetails> sales = query.getResultList();
        for (InvoiceDetails sale : sales) {
            logger.log(Level.WARNING, "sale >>>>>>>>>>" + sale.getIsbn());
        }
        return (List<InvoiceDetails>) sales;

    }

    public List<Invoice> getTotalInvoicesByClient(Date from, Date to) {
        logger.log(Level.WARNING, "incoming from date " + from.toString());
        logger.log(Level.WARNING, "incoming to date " + to.toString());
        TypedQuery<Invoice> query = em.createQuery("SELECT i FROM Invoice i "
                + "where i.dateOfSale BETWEEN :from AND :to order by i.dateOfSale desc", Invoice.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        logger.log(Level.WARNING, "get total sales query>>>>>>>>>>");
        Collection<Invoice> sales = query.getResultList();

        return (List<Invoice>) sales;
    }

    public List<InvoiceDetails> getTotalInvoicesDetailsByClient(Date from, Date to) {
        logger.log(Level.WARNING, "incoming from date " + from.toString());
        logger.log(Level.WARNING, "incoming to date " + to.toString());
        TypedQuery<InvoiceDetails> query = em.createQuery("SELECT ivd FROM InvoiceDetails ivd "
                + "where ivd.invoiceId.dateOfSale BETWEEN :from AND :to order by ivd.invoiceId.dateOfSale desc", InvoiceDetails.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        logger.log(Level.WARNING, "get total invoices details by client  query>>>>>>>>>>");
        Collection<InvoiceDetails> sales = query.getResultList();
        for (InvoiceDetails sale : sales) {
            logger.log(Level.WARNING, "sale >>>>>>>>>>" + sale.getIsbn());
        }
        return (List<InvoiceDetails>) sales;
    }

    public List<Object[]> getTotalInvoiceDetailsByAuthor(Date from, Date to) {

        TypedQuery<Object[]> query = em.createQuery("SELECT i, ivd FROM InvoiceDetails ivd "
                + "inner join ivd.invoiceId i where i.dateOfSale BETWEEN :from AND :to order by i.dateOfSale desc", Object[].class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        Collection<Object[]> sales = query.getResultList();
        for (Object[] sale : sales) {
            logger.log(Level.WARNING, "sale at 0 invoice details by author" + sale[0].toString());
            logger.log(Level.WARNING, "sale >>>>>>>>>>" + sale[1].toString());
        }

        return (List<Object[]>) sales;
    }

    public List<Object[]> getTotalSalesByPublisher(Date date, Date date0) {

        TypedQuery<Object[]> query = em.createQuery("SELECT i.dateOfSale, publisher, ivd FROM InvoiceDetails ivd "
                + "inner join ivd.invoiceId i inner join ivd.isbn b inner join b.publisherId publisher order by i.dateOfSale desc", Object[].class);

        Collection<Object[]> sales = query.getResultList();

        return (List<Object[]>) sales;
    }

    public Integer getTotalSold(String isbn, Date from, Date to) {
        logger.log(Level.INFO, " IN Total sold " + isbn);

        String query = "Select COUNT(inv.isbn) FROM InvoiceDetails inv "
                + "WHERE inv.isbn.isbn=:luIsbn and "
                + "inv.invoiceId.dateOfSale BETWEEN :from AND :to "
                + "GROUP BY inv.isbn";

        TypedQuery<Long> totalSold = em.createQuery(query, Long.class);
        totalSold.setParameter("from", from);
        totalSold.setParameter("to", to);
        totalSold.setParameter("luIsbn", isbn);

        if (totalSold.getResultList() == null || totalSold.getResultList().isEmpty()) {
            logger.log(Level.INFO, "List empty");
            return (int) 0;
        }
        logger.log(Level.INFO, "Total sold " + totalSold.getResultList().get(0));
        long l = totalSold.getResultList().get(0);
        int i = (int) l;
        return i;
    }

    public List<Object[]> getTopSellingBooks(Date from, Date to) {

        TypedQuery<Object[]> query = em.createQuery("SELECT  count(i.invoiceId.isbn.isbn) FROM Invoice i where i.dateOfSale BETWEEN :from AND :to GROUP BY i.invoiceId.isbn.isbn", Object[].class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        Collection<Object[]> books = query.getResultList();
        return (List<Object[]>) books;
    }

    public List<Object[]> getTopClients(Date from, Date to) {
        logger.log(Level.WARNING, "incoming from date " + from.toString());
        logger.log(Level.WARNING, "incoming to date " + to.toString());
        TypedQuery<Object[]> query = em.createQuery("SELECT i, sum(i.grossValue) FROM Invoice i "
                + "where i.dateOfSale BETWEEN :from AND :to group by i.clientId order by i.dateOfSale desc", Object[].class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        logger.log(Level.WARNING, "get total invoices details by client  query>>>>>>>>>>");
        Collection<Object[]> sales = query.getResultList();

        return (List<Object[]>) sales;
    }

    public List<Object[]> getTopSellers(Date from, Date to) {
        TypedQuery<Object[]> query = em.createQuery("SELECT i, ivd, count(ivd.isbn.isbn) FROM InvoiceDetails ivd "
                + "inner join ivd.invoiceId i where i.dateOfSale BETWEEN :from AND :to group by ivd.isbn.isbn order by count(ivd.isbn.isbn) desc", Object[].class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        Collection<Object[]> sales = query.getResultList();
        return (List<Object[]>) sales;
    }
}
