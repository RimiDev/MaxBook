package com.rimidev.maxbook.controller;

import com.rimidev.maxbook.controller.exceptions.IllegalOrphanException;
import com.rimidev.maxbook.controller.exceptions.NonexistentEntityException;
import com.rimidev.maxbook.controller.exceptions.PreexistingEntityException;
import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Ads;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.rimidev.maxbook.entities.Publisher;
import com.rimidev.maxbook.entities.Author;
import com.rimidev.maxbook.entities.Book;
import java.util.ArrayList;
import java.util.List;
import com.rimidev.maxbook.entities.InvoiceDetails;
import com.rimidev.maxbook.entities.Review;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Philippe Langlois-Pedroso
 */
@Named
@RequestScoped
public class BookJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    private Logger logger = Logger.getLogger(ClientJpaController.class.getName());

    @PersistenceContext
    private EntityManager em;

    public void create(Book book) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (book.getAuthorList() == null) {
            book.setAuthorList(new ArrayList<Author>());
        }
        if (book.getInvoiceDetailsList() == null) {
            book.setInvoiceDetailsList(new ArrayList<InvoiceDetails>());
        }
        if (book.getReviewList() == null) {
            book.setReviewList(new ArrayList<Review>());
        }

        try {
            utx.begin();
            Publisher publisherId = book.getPublisherId();
            if (publisherId != null) {
                publisherId = em.getReference(publisherId.getClass(), publisherId.getId());
                book.setPublisherId(publisherId);
            }
            List<Author> attachedAuthorList = new ArrayList<Author>();
            for (Author authorListAuthorToAttach : book.getAuthorList()) {
                authorListAuthorToAttach = em.getReference(authorListAuthorToAttach.getClass(), authorListAuthorToAttach.getId());
                attachedAuthorList.add(authorListAuthorToAttach);
            }
            book.setAuthorList(attachedAuthorList);
            List<InvoiceDetails> attachedInvoiceDetailsList = new ArrayList<InvoiceDetails>();
            for (InvoiceDetails invoiceDetailsListInvoiceDetailsToAttach : book.getInvoiceDetailsList()) {
                invoiceDetailsListInvoiceDetailsToAttach = em.getReference(invoiceDetailsListInvoiceDetailsToAttach.getClass(), invoiceDetailsListInvoiceDetailsToAttach.getId());
                attachedInvoiceDetailsList.add(invoiceDetailsListInvoiceDetailsToAttach);
            }
            book.setInvoiceDetailsList(attachedInvoiceDetailsList);
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : book.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getId());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            book.setReviewList(attachedReviewList);
            em.persist(book);
            if (publisherId != null) {
                publisherId.getBookList().add(book);
                publisherId = em.merge(publisherId);
            }
            for (Author authorListAuthor : book.getAuthorList()) {
                authorListAuthor.getBookList().add(book);
                authorListAuthor = em.merge(authorListAuthor);
            }
            for (InvoiceDetails invoiceDetailsListInvoiceDetails : book.getInvoiceDetailsList()) {
                Book oldIsbnOfInvoiceDetailsListInvoiceDetails = invoiceDetailsListInvoiceDetails.getIsbn();
                invoiceDetailsListInvoiceDetails.setIsbn(book);
                invoiceDetailsListInvoiceDetails = em.merge(invoiceDetailsListInvoiceDetails);
                if (oldIsbnOfInvoiceDetailsListInvoiceDetails != null) {
                    oldIsbnOfInvoiceDetailsListInvoiceDetails.getInvoiceDetailsList().remove(invoiceDetailsListInvoiceDetails);
                    oldIsbnOfInvoiceDetailsListInvoiceDetails = em.merge(oldIsbnOfInvoiceDetailsListInvoiceDetails);
                }
            }
            for (Review reviewListReview : book.getReviewList()) {
                Book oldIsbnOfReviewListReview = reviewListReview.getIsbn();
                reviewListReview.setIsbn(book);
                reviewListReview = em.merge(reviewListReview);
                if (oldIsbnOfReviewListReview != null) {
                    oldIsbnOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldIsbnOfReviewListReview = em.merge(oldIsbnOfReviewListReview);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBook(book.getIsbn()) != null) {
                throw new PreexistingEntityException("Book " + book + " already exists.", ex);
            }
            throw ex;
        }
    }

    public void edit(Book book) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        try {
            utx.begin();
            Book persistentBook = em.find(Book.class, book.getIsbn());
            Publisher publisherIdOld = persistentBook.getPublisherId();
            Publisher publisherIdNew = book.getPublisherId();
            List<Author> authorListOld = persistentBook.getAuthorList();
            List<Author> authorListNew = book.getAuthorList();
            List<InvoiceDetails> invoiceDetailsListOld = persistentBook.getInvoiceDetailsList();
            List<InvoiceDetails> invoiceDetailsListNew = book.getInvoiceDetailsList();
            List<Review> reviewListOld = persistentBook.getReviewList();
            List<Review> reviewListNew = book.getReviewList();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetails invoiceDetailsListOldInvoiceDetails : invoiceDetailsListOld) {
                if (!invoiceDetailsListNew.contains(invoiceDetailsListOldInvoiceDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetails " + invoiceDetailsListOldInvoiceDetails + " since its isbn field is not nullable.");
                }
            }
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewListOldReview + " since its isbn field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (publisherIdNew != null) {
                publisherIdNew = em.getReference(publisherIdNew.getClass(), publisherIdNew.getId());
                book.setPublisherId(publisherIdNew);
            }
            List<Author> attachedAuthorListNew = new ArrayList<Author>();
            for (Author authorListNewAuthorToAttach : authorListNew) {
                authorListNewAuthorToAttach = em.getReference(authorListNewAuthorToAttach.getClass(), authorListNewAuthorToAttach.getId());
                attachedAuthorListNew.add(authorListNewAuthorToAttach);
            }
            authorListNew = attachedAuthorListNew;
            book.setAuthorList(authorListNew);
            List<InvoiceDetails> attachedInvoiceDetailsListNew = new ArrayList<InvoiceDetails>();
            for (InvoiceDetails invoiceDetailsListNewInvoiceDetailsToAttach : invoiceDetailsListNew) {
                invoiceDetailsListNewInvoiceDetailsToAttach = em.getReference(invoiceDetailsListNewInvoiceDetailsToAttach.getClass(), invoiceDetailsListNewInvoiceDetailsToAttach.getId());
                attachedInvoiceDetailsListNew.add(invoiceDetailsListNewInvoiceDetailsToAttach);
            }
            invoiceDetailsListNew = attachedInvoiceDetailsListNew;
            book.setInvoiceDetailsList(invoiceDetailsListNew);
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getId());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            book.setReviewList(reviewListNew);
            book = em.merge(book);
            if (publisherIdOld != null && !publisherIdOld.equals(publisherIdNew)) {
                publisherIdOld.getBookList().remove(book);
                publisherIdOld = em.merge(publisherIdOld);
            }
            if (publisherIdNew != null && !publisherIdNew.equals(publisherIdOld)) {
                publisherIdNew.getBookList().add(book);
                publisherIdNew = em.merge(publisherIdNew);
            }
            for (Author authorListOldAuthor : authorListOld) {
                if (!authorListNew.contains(authorListOldAuthor)) {
                    authorListOldAuthor.getBookList().remove(book);
                    authorListOldAuthor = em.merge(authorListOldAuthor);
                }
            }
            for (Author authorListNewAuthor : authorListNew) {
                if (!authorListOld.contains(authorListNewAuthor)) {
                    authorListNewAuthor.getBookList().add(book);
                    authorListNewAuthor = em.merge(authorListNewAuthor);
                }
            }
            for (InvoiceDetails invoiceDetailsListNewInvoiceDetails : invoiceDetailsListNew) {
                if (!invoiceDetailsListOld.contains(invoiceDetailsListNewInvoiceDetails)) {
                    Book oldIsbnOfInvoiceDetailsListNewInvoiceDetails = invoiceDetailsListNewInvoiceDetails.getIsbn();
                    invoiceDetailsListNewInvoiceDetails.setIsbn(book);
                    invoiceDetailsListNewInvoiceDetails = em.merge(invoiceDetailsListNewInvoiceDetails);
                    if (oldIsbnOfInvoiceDetailsListNewInvoiceDetails != null && !oldIsbnOfInvoiceDetailsListNewInvoiceDetails.equals(book)) {
                        oldIsbnOfInvoiceDetailsListNewInvoiceDetails.getInvoiceDetailsList().remove(invoiceDetailsListNewInvoiceDetails);
                        oldIsbnOfInvoiceDetailsListNewInvoiceDetails = em.merge(oldIsbnOfInvoiceDetailsListNewInvoiceDetails);
                    }
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Book oldIsbnOfReviewListNewReview = reviewListNewReview.getIsbn();
                    reviewListNewReview.setIsbn(book);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldIsbnOfReviewListNewReview != null && !oldIsbnOfReviewListNewReview.equals(book)) {
                        oldIsbnOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldIsbnOfReviewListNewReview = em.merge(oldIsbnOfReviewListNewReview);
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
                String id = book.getIsbn();
                if (findBook(id) == null) {
                    throw new NonexistentEntityException("The book with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        try {
            utx.begin();
            Book book;
            try {
                book = em.getReference(Book.class, id);
                book.getIsbn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The book with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InvoiceDetails> invoiceDetailsListOrphanCheck = book.getInvoiceDetailsList();
            for (InvoiceDetails invoiceDetailsListOrphanCheckInvoiceDetails : invoiceDetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the InvoiceDetails " + invoiceDetailsListOrphanCheckInvoiceDetails + " in its invoiceDetailsList field has a non-nullable isbn field.");
            }
            List<Review> reviewListOrphanCheck = book.getReviewList();
            for (Review reviewListOrphanCheckReview : reviewListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the Review " + reviewListOrphanCheckReview + " in its reviewList field has a non-nullable isbn field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Publisher publisherId = book.getPublisherId();
            if (publisherId != null) {
                publisherId.getBookList().remove(book);
                publisherId = em.merge(publisherId);
            }
            List<Author> authorList = book.getAuthorList();
            for (Author authorListAuthor : authorList) {
                authorListAuthor.getBookList().remove(book);
                authorListAuthor = em.merge(authorListAuthor);
            }
            em.remove(book);
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

    public List<Book> findBookEntities() {
        return findBookEntities(true, -1, -1);
    }

    public void setSessionVariables() {
        logger.warning("inside setSessionVariables bookjpacontroller");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        Book book1 = findBook("978-0060256654");
        Integer book1Quantity = 2;

        Book book2 = findBook("978-0060555665");
        Integer book2Quantity = 5;

        HashMap<Book, Integer> cart = new HashMap<Book, Integer>();

        cart.put(book1, book1Quantity);
        cart.put(book2, book2Quantity);

        session.setAttribute("cartItems", cart);
        cart = (HashMap<Book, Integer>) session.getAttribute("cartItems");

    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return findBookEntities(false, maxResults, firstResult);
    }

    private List<Book> findBookEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Book.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

//    public List<Book> findBookEntities(int maxResults, int pageNumber) {
//        return findBookEntities(false, maxResults, pageNumber);
// }
    private List<Book> findBookEntities2(boolean all, int maxResults, int startResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Book.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(startResult);
        }
        return q.getResultList();

    }

    public Book findBook(String id) {

        return em.find(Book.class, id);
    }

    public Book findBookByIsbn(String isbn) {
        TypedQuery<Book> query
                = em.createNamedQuery("Country.findByIsbn", Book.class).setParameter("isbn", isbn);
        return query.getSingleResult();

    }

    public int getBookCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Book> rt = cq.from(Book.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

//    public int getBookCount2() {
//    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//    Root<Book> rt = cq.from(Book.class);
//    cq.select(em.getCriteriaBuilder().count(rt));
//    Query q = em.createQuery(cq);
//    return ((Long) q.getSingleResult()).intValue();
//  }
    public List<Book> getAllBooks() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Book.class));
        Query q = em.createQuery(cq);
        return q.getResultList();
    }

    public List<Book> getBooksByAuthor(List<Integer> authIds, String isbn) {
        logger.log(Level.INFO, authIds.toString());

        String query = "select distinct bk from Book bk INNER JOIN bk.authorList bkl where bkl.id IN :auths AND bk.isbn <> :bookIsbn";
        TypedQuery<Book> authBooks = em.createQuery(query, Book.class);
        authBooks.setParameter("auths", authIds);
        authBooks.setParameter("bookIsbn", isbn);

        logger.log(Level.INFO, "Author Books>> " + authBooks.setMaxResults(3).getResultList());

        return authBooks.getResultList();
//
//    List<Book> results = query.getResultList();
//
//    return results.get(0);
    }

    public List<Book> getBooksByAuthor(List<Author> auths) {
        return null;
//        use bookstore_db;
//
//        select ab.title,ab.fullname from (select book.isbn,title, concat(first_name,' ',last_name) 
//        as fullname from book join author_book on book.isbn = author_book.isbn join author on author.id = author_book.author_id)
//        as ab where ab.fullname in ("Adam Gasiewski","Emily Beck");
    }

    /**
     * Find all books by title
     *
     * @return List of books with given title
     * @param Title given by the user
     * @author Maxime Lacasse
     *
     */
    public List<Book> getBookByTitle(String title) {

        TypedQuery<Book> query = em.createNamedQuery("Book.findByLikeTitle", Book.class);

        query.setParameter("title", "%" + title + "%");

        List<Book> books = query.getResultList();

        return (List<Book>) books;

    }

    public List<Book> getBookByGenre(String genre) {

        TypedQuery<Book> query = em.createNamedQuery("Book.findByLikeGenre", Book.class);

        query.setParameter("genre", "%" + genre + "%");
//        query.setParameter("genre", genre);

        List<Book> books = query.getResultList();

        return (List<Book>) books;

    }

    public List<Book> getEmptyList() {

        TypedQuery<Book> query = em.createNamedQuery("Book.findNoBooks", Book.class);
        List<Book> books = query.getResultList();

        return (List<Book>) books;

    }

    public List<Book> searchBooks(String criteria) {
        logger.log(Level.INFO, "Search Criteria >>> " + criteria);

        String quer = "select distinct bk from Book bk inner join bk.authorList bkl inner join bk.publisherId bkp where bk.isbn LIKE :crit OR bk.title LIKE :crit OR bkl.firstName LIKE :crit "
                + "OR bkl.lastName LIKE :crit OR bkp.name LIKE :crit";

        TypedQuery<Book> searchedBooks = em.createQuery(quer, Book.class);
        searchedBooks.setParameter("crit", "%" + criteria + "%");

        logger.log(Level.INFO, "Author Books>> " + searchedBooks.getResultList());
        return searchedBooks.getResultList();

    }

}
