package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.controller.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maximelacasse
 */
@Entity
@Table(name = "ads")
@NamedQueries({
    @NamedQuery(name = "Ads.findAll", query = "SELECT a FROM Ads a")
    , @NamedQuery(name = "Ads.findById", query = "SELECT a FROM Ads a WHERE a.id = :id")
    , @NamedQuery(name = "Ads.findByImageName", query = "SELECT a FROM Ads a WHERE a.imageName = :imageName")
    , @NamedQuery(name = "Ads.findByActive", query = "SELECT a FROM Ads a WHERE a.active = :active")})
public class Ads implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "imageName")
    private String imageName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "siteLink")
    private String siteLink;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "active")
    private String active;

    public Ads() {
    }

    public Ads(Integer id) {
        this.id = id;
    }

    public Ads(Integer id, String imageName) {
        this.id = id;
        this.imageName = imageName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    public String getSiteLink(){
        return siteLink;
    }
    
    public void setSiteLink(String siteLink){
        this.siteLink = siteLink;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    
    public boolean getActiveBoolean() {
        if (active != null) {
            return active.charAt(0) != '0';
        }
        return false;
    }

    public void setActiveBoolean(boolean active) {
        if (active == true) {
            this.active = "1";
        } else {
            this.active = "0";
        }

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
        if (!(object instanceof Ads)) {
            return false;
        }
        Ads other = (Ads) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rimidev.maxbook.controller.Ads[ id=" + id + " ]";
    }

    
}

