/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.util;

import com.rimidev.maxbook.beans.PublishersBean;
import com.rimidev.maxbook.entities.Publisher;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rhai
 */
@FacesConverter(value = "publisherConverter")
public class PublisherConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String pubId) {
        ValueExpression vex = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(),
                "#{publishersBean}",PublishersBean.class);
        PublishersBean pubs = (PublishersBean)vex.getValue(context.getELContext());
        return pubs.getPub(Integer.valueOf(pubId));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object publisher) {
        return ((Publisher)publisher).getId().toString();
    }
    
}
