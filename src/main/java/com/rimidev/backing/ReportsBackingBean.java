/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.backing;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * manager can request a number of reports on the inventory, orders and clients. 
 * 
 * all reports accepta  date range and any other details
 * report : summary or details, shows total sales, cost and profit.
 *        
 * 
 *
 * @author eric
 */
@Named
@SessionScoped
public class ReportsBackingBean implements Serializable {
  
}
