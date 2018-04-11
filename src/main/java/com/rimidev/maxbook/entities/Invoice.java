/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 1513733
 */
@Entity
@Table(name = "invoice", catalog = "bookstore_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i")
    , @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :id")
    , @NamedQuery(name = "Invoice.findByDateOfSale", query = "SELECT i FROM Invoice i WHERE i.dateOfSale = :dateOfSale")
    , @NamedQuery(name = "Invoice.findByNetValue", query = "SELECT i FROM Invoice i WHERE i.netValue = :netValue")
    , @NamedQuery(name = "Invoice.findByGrossValue", query = "SELECT i FROM Invoice i WHERE i.grossValue = :grossValue")
    , @NamedQuery(name = "Invoice.findByRemovalStatus", query = "SELECT i FROM Invoice i WHERE i.removalStatus = :removalStatus")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_of_sale")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfSale;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "net_value")
    private BigDecimal netValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gross_value")
    private BigDecimal grossValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "removal_status")
    private boolean removalStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId", fetch = FetchType.LAZY)
    private List<InvoiceDetails> invoiceDetailsList;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client clientId;

    public Invoice() {
    }

    public Invoice(Integer id) {
        this.id = id;
    }

    public Invoice(Integer id, Date dateOfSale, BigDecimal netValue, BigDecimal grossValue, boolean removalStatus) {
        this.id = id;
        this.dateOfSale = dateOfSale;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.removalStatus = removalStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public boolean getRemovalStatus() {
        return removalStatus;
    }

    public void setRemovalStatus(boolean removalStatus) {
        this.removalStatus = removalStatus;
    }

    public List<InvoiceDetails> getInvoiceDetailsList() {
        return invoiceDetailsList;
    }

    public void setInvoiceDetailsList(List<InvoiceDetails> invoiceDetailsList) {
        this.invoiceDetailsList = invoiceDetailsList;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
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
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rimidev.maxbook.entities.Invoice[ id=" + id + " ]";
    }
    
}
