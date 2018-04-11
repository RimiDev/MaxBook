/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 1513733
 */
@Entity
@Table(name = "invoice_details", catalog = "bookstore_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvoiceDetails.findAll", query = "SELECT i FROM InvoiceDetails i")
    , @NamedQuery(name = "InvoiceDetails.findById", query = "SELECT i FROM InvoiceDetails i WHERE i.id = :id")
    , @NamedQuery(name = "InvoiceDetails.findByBookPrice", query = "SELECT i FROM InvoiceDetails i WHERE i.bookPrice = :bookPrice")
    , @NamedQuery(name = "InvoiceDetails.findByPSTrate", query = "SELECT i FROM InvoiceDetails i WHERE i.pSTrate = :pSTrate")
    , @NamedQuery(name = "InvoiceDetails.findByGSTrate", query = "SELECT i FROM InvoiceDetails i WHERE i.gSTrate = :gSTrate")
    , @NamedQuery(name = "InvoiceDetails.findByHSTrate", query = "SELECT i FROM InvoiceDetails i WHERE i.hSTrate = :hSTrate")
    , @NamedQuery(name = "InvoiceDetails.findByRemovalStatus", query = "SELECT i FROM InvoiceDetails i WHERE i.removalStatus = :removalStatus")})
public class InvoiceDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "book_price")
    private BigDecimal bookPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PST_rate")
    private BigDecimal pSTrate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GST_rate")
    private BigDecimal gSTrate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HST_rate")
    private BigDecimal hSTrate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "removal_status")
    private boolean removalStatus;
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Invoice invoiceId;
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Book isbn;

    public InvoiceDetails() {
    }

    public InvoiceDetails(Integer id) {
        this.id = id;
    }

    public InvoiceDetails(Integer id, BigDecimal bookPrice, BigDecimal pSTrate, BigDecimal gSTrate, BigDecimal hSTrate, boolean removalStatus) {
        this.id = id;
        this.bookPrice = bookPrice;
        this.pSTrate = pSTrate;
        this.gSTrate = gSTrate;
        this.hSTrate = hSTrate;
        this.removalStatus = removalStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public BigDecimal getPSTrate() {
        return pSTrate;
    }

    public void setPSTrate(BigDecimal pSTrate) {
        this.pSTrate = pSTrate;
    }

    public BigDecimal getGSTrate() {
        return gSTrate;
    }

    public void setGSTrate(BigDecimal gSTrate) {
        this.gSTrate = gSTrate;
    }

    public BigDecimal getHSTrate() {
        return hSTrate;
    }

    public void setHSTrate(BigDecimal hSTrate) {
        this.hSTrate = hSTrate;
    }

    public boolean getRemovalStatus() {
        return removalStatus;
    }

    public void setRemovalStatus(boolean removalStatus) {
        this.removalStatus = removalStatus;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoiceDetails)) {
            return false;
        }
        InvoiceDetails other = (InvoiceDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rimidev.maxbook.entities.InvoiceDetails[ id=" + id + " ]";
    }
    
}
