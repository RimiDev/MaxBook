/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.entities;

/**
 *
 * @author 1513733
 */
public class Book_Sale {
    
    private Book bk;
    private Integer totalSales;
    
    public Book_Sale(Book bk,Integer totalSales){
        this.bk = bk;
        this.totalSales = totalSales;
    }

    public Book getBk() {
        return bk;
    }

    public void setBk(Book bk) {
        this.bk = bk;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }
    
    
}
