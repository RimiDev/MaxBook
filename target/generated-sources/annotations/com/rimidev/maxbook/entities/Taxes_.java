package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Client;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-20T15:34:53")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-13T17:00:32")
>>>>>>> 53f50317d7588c62e2c299abb9b4d6c7f0bf970a
@StaticMetamodel(Taxes.class)
public class Taxes_ { 

    public static volatile SingularAttribute<Taxes, BigDecimal> pSTrate;
    public static volatile SingularAttribute<Taxes, BigDecimal> gSTrate;
    public static volatile SingularAttribute<Taxes, String> province;
    public static volatile SingularAttribute<Taxes, BigDecimal> hSTrate;
    public static volatile ListAttribute<Taxes, Client> clientList;

}