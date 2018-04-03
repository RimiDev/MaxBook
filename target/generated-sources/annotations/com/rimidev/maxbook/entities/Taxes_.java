package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Client;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-27T17:20:44")
=======
<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-28T10:38:45")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-27T15:19:45")
>>>>>>> b16caab077586dad423b7a90c32b4432baf09d49
>>>>>>> 334f51c566c674ea95e962500f0e0853f29a0799
@StaticMetamodel(Taxes.class)
public class Taxes_ { 

    public static volatile SingularAttribute<Taxes, BigDecimal> pSTrate;
    public static volatile SingularAttribute<Taxes, BigDecimal> gSTrate;
    public static volatile SingularAttribute<Taxes, String> province;
    public static volatile SingularAttribute<Taxes, BigDecimal> hSTrate;
    public static volatile ListAttribute<Taxes, Client> clientList;

}