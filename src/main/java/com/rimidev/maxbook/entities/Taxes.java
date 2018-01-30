/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 1513733
 */
@Entity
@Table(name = "taxes", catalog = "BookStore_DB", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxes.findAll", query = "SELECT t FROM Taxes t")
    , @NamedQuery(name = "Taxes.findByProvince", query = "SELECT t FROM Taxes t WHERE t.province = :province")
    , @NamedQuery(name = "Taxes.findByPSTrate", query = "SELECT t FROM Taxes t WHERE t.pSTrate = :pSTrate")
    , @NamedQuery(name = "Taxes.findByGSTrate", query = "SELECT t FROM Taxes t WHERE t.gSTrate = :gSTrate")
    , @NamedQuery(name = "Taxes.findByHSTrate", query = "SELECT t FROM Taxes t WHERE t.hSTrate = :hSTrate")})
public class Taxes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "province")
    private String province;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "province", fetch = FetchType.LAZY)
    private List<Client> clientList;

    public Taxes() {
    }

    public Taxes(String province) {
        this.province = province;
    }

    public Taxes(String province, BigDecimal pSTrate, BigDecimal gSTrate, BigDecimal hSTrate) {
        this.province = province;
        this.pSTrate = pSTrate;
        this.gSTrate = gSTrate;
        this.hSTrate = hSTrate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    @XmlTransient
    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (province != null ? province.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxes)) {
            return false;
        }
        Taxes other = (Taxes) object;
        if ((this.province == null && other.province != null) || (this.province != null && !this.province.equals(other.province))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rimidev.maxbook.entities.Taxes[ province=" + province + " ]";
    }
    
}
