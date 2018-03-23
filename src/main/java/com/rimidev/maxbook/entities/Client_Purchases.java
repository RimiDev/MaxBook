/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.entities;

import java.math.BigDecimal;

/**
 *
 * @author 1513733
 */
public class Client_Purchases {
    
    private Client clnt;
    private BigDecimal spent;

    public Client_Purchases(Client clnt, BigDecimal spent) {
        this.clnt = clnt;
        this.spent = spent;
    }

    public Client getClnt() {
        return clnt;
    }

    public void setClnt(Client clnt) {
        this.clnt = clnt;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }
    
    
    
    
}
