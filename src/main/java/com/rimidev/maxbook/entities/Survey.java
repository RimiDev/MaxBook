/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.entities;

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

/**
 *
 * @author 1513733
 */
@Entity
@Table(name = "survey", catalog = "bookstore_db", schema = "")
@NamedQueries({
    @NamedQuery(name = "Survey.findAll", query = "SELECT s FROM Survey s")
    , @NamedQuery(name = "Survey.findById", query = "SELECT s FROM Survey s WHERE s.id = :id")
    , @NamedQuery(name = "Survey.findByQuestion", query = "SELECT s FROM Survey s WHERE s.question = :question")
    , @NamedQuery(name = "Survey.findByOption1", query = "SELECT s FROM Survey s WHERE s.option1 = :option1")
    , @NamedQuery(name = "Survey.findByOption2", query = "SELECT s FROM Survey s WHERE s.option2 = :option2")
    , @NamedQuery(name = "Survey.findByOption3", query = "SELECT s FROM Survey s WHERE s.option3 = :option3")
    , @NamedQuery(name = "Survey.findByOption4", query = "SELECT s FROM Survey s WHERE s.option4 = :option4")
    , @NamedQuery(name = "Survey.findByCount1", query = "SELECT s FROM Survey s WHERE s.count1 = :count1")
    , @NamedQuery(name = "Survey.findByCount2", query = "SELECT s FROM Survey s WHERE s.count2 = :count2")
    , @NamedQuery(name = "Survey.findByCount3", query = "SELECT s FROM Survey s WHERE s.count3 = :count3")
    , @NamedQuery(name = "Survey.findByCount4", query = "SELECT s FROM Survey s WHERE s.count4 = :count4")
    , @NamedQuery(name = "Survey.findByRemovalStatus", query = "SELECT s FROM Survey s WHERE s.removalStatus = :removalStatus")})
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "option1")
    private String option1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "option2")
    private String option2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "option3")
    private String option3;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "option4")
    private String option4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count1")
    private int count1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count2")
    private int count2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count3")
    private int count3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count4")
    private int count4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "removal_status")
    private boolean removalStatus;

    public Survey() {
    }

    public Survey(Integer id) {
        this.id = id;
    }

    public Survey(Integer id, String question, String option1, String option2, String option3, String option4, int count1, int count2, int count3, int count4, boolean removalStatus) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.count4 = count4;
        this.removalStatus = removalStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public int getCount3() {
        return count3;
    }

    public void setCount3(int count3) {
        this.count3 = count3;
    }

    public int getCount4() {
        return count4;
    }

    public void setCount4(int count4) {
        this.count4 = count4;
    }

    public boolean getRemovalStatus() {
        return removalStatus;
    }

    public void setRemovalStatus(boolean removalStatus) {
        this.removalStatus = removalStatus;
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
        if (!(object instanceof Survey)) {
            return false;
        }
        Survey other = (Survey) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rimidev.maxbook.entities.Survey[ id=" + id + " ]";
    }
    
}
