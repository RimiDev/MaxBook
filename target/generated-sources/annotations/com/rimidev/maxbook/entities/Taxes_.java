package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Client;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T18:20:08")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T19:46:26")
>>>>>>> 6d1b65f471e0c4ae210eeebf945823f1dcb3c95b
@StaticMetamodel(Taxes.class)
public class Taxes_ { 

    public static volatile SingularAttribute<Taxes, BigDecimal> pSTrate;
    public static volatile SingularAttribute<Taxes, BigDecimal> gSTrate;
    public static volatile SingularAttribute<Taxes, String> province;
    public static volatile SingularAttribute<Taxes, BigDecimal> hSTrate;
    public static volatile ListAttribute<Taxes, Client> clientList;

}