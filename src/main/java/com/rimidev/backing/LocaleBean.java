package com.rimidev.backing;

import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LocaleBean implements Serializable {

    private Locale locale;
    private static final Logger logger = Logger.getLogger(AllBooksBackingBean.class.getName());

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String a, String b) {
        logger.info(locale.toString());
        locale = new Locale(a, b);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

}
