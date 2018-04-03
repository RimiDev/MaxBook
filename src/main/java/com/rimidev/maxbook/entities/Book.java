/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.entities;

import com.rimidev.backing.BookDisplayBacking;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 1513733
 */
@Entity
@Table(name = "book", catalog = "BookStore_DB", schema = "")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
  , @NamedQuery(name = "Book.findByIsbn", query = "SELECT b FROM Book b WHERE b.isbn = :isbn")
  , @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title = :title")
  , @NamedQuery(name = "Book.findByPublishDate", query = "SELECT b FROM Book b WHERE b.publishDate = :publishDate")
  , @NamedQuery(name = "Book.findByPages", query = "SELECT b FROM Book b WHERE b.pages = :pages")
  , @NamedQuery(name = "Book.findByGenre", query = "SELECT b FROM Book b WHERE b.genre = :genre")
  , @NamedQuery(name = "Book.findByListPrice", query = "SELECT b FROM Book b WHERE b.listPrice = :listPrice")
  , @NamedQuery(name = "Book.findBySalePrice", query = "SELECT b FROM Book b WHERE b.salePrice = :salePrice")
  , @NamedQuery(name = "Book.findByWholesalePrice", query = "SELECT b FROM Book b WHERE b.wholesalePrice = :wholesalePrice")
  , @NamedQuery(name = "Book.findByFormat", query = "SELECT b FROM Book b WHERE b.format = :format")
  , @NamedQuery(name = "Book.findByEnteredDate", query = "SELECT b FROM Book b WHERE b.enteredDate = :enteredDate")
  , @NamedQuery(name = "Book.findByRemovalStatus", query = "SELECT b FROM Book b WHERE b.removalStatus = :removalStatus")
  , @NamedQuery(name = "Book.findByDescription", query = "SELECT b FROM Book b WHERE b.description = :description")
  , @NamedQuery(name = "Book.findByLikeTitle", query = "SELECT b FROM Book b WHERE b.title LIKE :title")
  , @NamedQuery(name = "Book.findByLikeGenre", query = "SELECT b FROM Book b WHERE b.genre LIKE :genre")
  , @NamedQuery(name = "Book.findNoBooks", query = "SELECT b FROM Book b WHERE b.isbn = '978-1449474256' ")})
public class Book implements Serializable {

 
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 14)
  @Column(name = "isbn")
  private String isbn;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "title")
  private String title;
  @Basic(optional = false)
  @NotNull
  @Column(name = "publish_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date publishDate;
  @Basic(optional = false)
  @NotNull
  @Column(name = "pages")
  private int pages;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "genre")
  private String genre;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Column(name = "list_price")
  private BigDecimal listPrice;
  @Basic(optional = false)
  @NotNull
  @Column(name = "sale_price")
  private BigDecimal salePrice;
  @Basic(optional = false)
  @NotNull
  @Column(name = "wholesale_price")
  private BigDecimal wholesalePrice;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "format")
  private String format;
  @Basic(optional = false)
  @NotNull
  @Column(name = "entered_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date enteredDate;
  @Basic(optional = false)
  @NotNull
  @Column(name = "removal_status")
  private Character removalStatus;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2184)
  @Column(name = "description")
  private String description;
  @ManyToMany(mappedBy = "bookList", fetch = FetchType.LAZY)
  private List<Author> authorList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn", fetch = FetchType.LAZY)
  private List<InvoiceDetails> invoiceDetailsList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn", fetch = FetchType.LAZY)
  private List<Review> reviewList;
  @JoinColumn(name = "publisher_id", referencedColumnName = "id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Publisher publisherId;

  public Book() {
  }

  public Book(String isbn) {
    this.isbn = isbn;
  }

  public Book(String isbn, String title, Date publishDate, int pages, String genre, BigDecimal listPrice, BigDecimal salePrice, BigDecimal wholesalePrice, String format, Date enteredDate, Character removalStatus, String description) {
    this.isbn = isbn;
    this.title = title;
    this.publishDate = publishDate;
    this.pages = pages;
    this.genre = genre;
    this.listPrice = listPrice;
    this.salePrice = salePrice;
    this.wholesalePrice = wholesalePrice;
    this.format = format;
    this.enteredDate = enteredDate;
    this.removalStatus = removalStatus;
    this.description = description;
  }

  public String getIsbn() {
    return isbn;
  }
  

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
          if (title != null && title.length()>50){
            return title.substring(0,50) + "...";
        }
       return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(Date publishDate) {
    this.publishDate = publishDate;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public BigDecimal getListPrice() {
    return listPrice;
  }

  public void setListPrice(BigDecimal listPrice) {
    this.listPrice = listPrice;
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  public BigDecimal getWholesalePrice() {
    return wholesalePrice;
  }

  public void setWholesalePrice(BigDecimal wholesalePrice) {
    this.wholesalePrice = wholesalePrice;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Date getEnteredDate() {
    return enteredDate;
  }

  public void setEnteredDate(Date enteredDate) {
    this.enteredDate = enteredDate;
  }

  public boolean getRemovalStatusBoolean() {
      if(removalStatus != null)
        return removalStatus.charValue() != '0';
      return false;
  }
 

  public void setRemovalStatusBoolean(boolean removalStatus) {
    if (removalStatus == true)
      this.removalStatus = '1';
    else
       this.removalStatus = '0';
    
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @XmlTransient
  public List<Author> getAuthorList() {
    return authorList;
  }

  public void setAuthorList(List<Author> authorList) {
    this.authorList = authorList;
  }

  @XmlTransient
  public List<InvoiceDetails> getInvoiceDetailsList() {
    return invoiceDetailsList;
  }

  public void setInvoiceDetailsList(List<InvoiceDetails> invoiceDetailsList) {
    this.invoiceDetailsList = invoiceDetailsList;
  }

  @XmlTransient
  public List<Review> getReviewList() {
    return reviewList;
  }

  public void setReviewList(List<Review> reviewList) {
    this.reviewList = reviewList;
  }

  public Publisher getPublisherId() {
    return publisherId;
  }

  public void setPublisherId(Publisher publisherId) {
    this.publisherId = publisherId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (isbn != null ? isbn.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Book)) {
      return false;
    }
    Book other = (Book) object;
    if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
      return false;
    }
    return true;
  }

    @Override
    public String toString() {
        return "Book{" + "isbn=" + isbn + ", title=" + title + ", publishDate=" + publishDate + ", pages=" + pages + ", genre=" + genre + ", listPrice=" + listPrice + ", salePrice=" + salePrice + ", wholesalePrice=" + wholesalePrice + ", format=" + format + ", enteredDate=" + enteredDate + ", removalStatus=" + removalStatus + ", description=" + description + ", authorList=" + authorList + ", invoiceDetailsList=" + invoiceDetailsList + ", reviewList=" + reviewList + ", publisherId=" + publisherId + '}';
    }

 

}
