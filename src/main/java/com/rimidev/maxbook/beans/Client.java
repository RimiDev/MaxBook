/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author maximelacasse
 */
@Named("client")
@RequestScoped
public class Client implements Serializable{
    
    //Client information
    private String firstName;
    private String lastName;
    private String title;
    private String company;
    private String email;
    private String cellPhone;
    private String homePhone;
    private String postalCode;
    private String country;
    private String city;
    private String province;
    
    //Country names for drop-down list
    private ArrayList<String> countryOptions;
    
    
    /**
     * Creates a new instance of Client
     */
    public Client() {
        //Pre-populating the options
        getCountryNames();
        
        //Can set any variable to anything and it will show in the sign in page.
        //Ex:firstName = "Max"
    }
    
    /*
     Setters and getters
    */

    public ArrayList<String> getCountryOptions() {
        return countryOptions;
    }

    public void setCountryOptions(ArrayList<String> countryOptions) {
        this.countryOptions = countryOptions;
    }

    
    
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    private String address;
    private String address2;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    /**
     * This grabs the country names
     * @return List of countries
     */
    private void getCountryNames(){
        //Get all the available locales
        Locale[] locale = Locale.getAvailableLocales();
        //Create an arrayList of strings to place the countries.
        ArrayList<String> countries = new ArrayList<String>();
        //SIngle country, which will be added to the arrayList.
        String country;
        //Iterate through all of the locales and add it to the countries
        //Check for duplicates and if the country's length is 0 (empty).
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        //Sort the arrayList
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        this.countryOptions = countries;
    }
    
    
    
}
