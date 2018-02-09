package com.rimidev.maxbook.entities;

import com.rimidev.maxbook.entities.Client;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-02-08T19:16:42")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-03T07:56:04")
>>>>>>> d4855c68de9d2fcb74b0bfe76a91e982c8665560
@StaticMetamodel(Taxes.class)
public class Taxes_ { 

    public static volatile SingularAttribute<Taxes, BigDecimal> pSTrate;
    public static volatile SingularAttribute<Taxes, BigDecimal> gSTrate;
    public static volatile SingularAttribute<Taxes, String> province;
    public static volatile SingularAttribute<Taxes, BigDecimal> hSTrate;
    public static volatile ListAttribute<Taxes, Client> clientList;

}