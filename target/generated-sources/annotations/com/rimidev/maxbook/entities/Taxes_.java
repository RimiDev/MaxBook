package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Client;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-26T03:08:04")
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-25T14:40:51")
>>>>>>> a7d211f7901e71e82e462fe41c27cf75d84ad32e
=======
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-03-26T18:58:51")
>>>>>>> 72a1cab27c2457743e3fe0f74380afc6cc5667b3
@StaticMetamodel(Taxes.class)
public class Taxes_ { 

    public static volatile SingularAttribute<Taxes, BigDecimal> pSTrate;
    public static volatile SingularAttribute<Taxes, BigDecimal> gSTrate;
    public static volatile SingularAttribute<Taxes, String> province;
    public static volatile SingularAttribute<Taxes, BigDecimal> hSTrate;
    public static volatile ListAttribute<Taxes, Client> clientList;

}