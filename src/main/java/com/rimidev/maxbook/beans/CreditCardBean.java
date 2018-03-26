package com.rimidev.maxbook.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author maximelacasse
 */
@Named
@SessionScoped
public class CreditCardBean implements Serializable {
    
    private String name;
    private String number;
    private String exp;
    private String cv;

    public CreditCardBean() {
        this.name = "";
        this.number = "";
        this.exp = "";
        this.cv = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
    
}
