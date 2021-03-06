package com.rimidev.prerenderview;

import com.rimidev.backing.LoginBackingBean;
import com.rimidev.maxbook.entities.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * This class contains methods to read and write cookies Cookies can be read at
 * any time but can only be written to before any HTML is delivered to the
 * browser. For that reason creating cookies is always a preRenderView type
 * event
 *
 * @author Ken
 */
@Named
@SessionScoped
public class PreRenderViewBean implements Serializable {

    // Default logger is java.util.logging
    private static final Logger LOG = Logger.getLogger("PreRenderViewBean.class");
    
    private String genre;

    public String getGenre() {
        LOG.info("GETTING GENRE FROM PRERENDERBEAN: " + genre);
        return genre;
    }

 
    /**
     * Look for a cookie
     */
    public void checkCookies() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookieMap = context.getExternalContext().getRequestCookieMap();

        // Retrieve all cookies
        if (cookieMap == null || cookieMap.isEmpty()) {
            LOG.info("No cookies");
        } else {
            ArrayList<Object> ac = new ArrayList<>(cookieMap.values());

            // Streams coding to print out the contenst of the cookies found
            ac.stream().map((c) -> {
                LOG.info(((Cookie) c).getName());
                return c;
            }).forEach((c) -> {
                LOG.info(((Cookie) c).getValue());
            });
        }

        // Retrieve a specific cookie
        Object my_cookie = context.getExternalContext().getRequestCookieMap().get("recentGenre");
        if (my_cookie != null) {
            genre = ((Cookie) my_cookie).getValue();
            LOG.info(((Cookie) my_cookie).getName());
            LOG.info(((Cookie) my_cookie).getValue());
             genre = ((Cookie) my_cookie).getValue();
        }
       
//        writeCookie(b);
    }

    public void writeCookie(String b) {
        
        if (b == null){
            genre = "Children";
        } else {
            genre = b;
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie("recentGenre", genre, null);
        
                Object my_cookie = context.getExternalContext().getRequestCookieMap().get("recentGenre");
                
        if (my_cookie != null) {
            genre = ((Cookie) my_cookie).getValue();
            LOG.info("MY COOKIE NAME:" + ((Cookie) my_cookie).getName());
            LOG.info("MY COOKIE VALUE:" + ((Cookie) my_cookie).getValue());
            LOG.info("GENRE VALUE: ---- " + genre );
        } else {
            //If there is no cookie set, i guess we will client track nothing?
            //Or we can hardcore a certain genre to client track?
            genre = "Children";
            LOG.info("NULL");
            LOG.info(my_cookie + "");
        }
    }
    
//    public void writeCookie(Book b) {
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
//        Cookie cookie = new Cookie("genreCookie", b.getGenre());
//        cookie.setMaxAge(60 * 60 * 24 * 365);
//        response.addCookie(cookie);
//
//    }


}
