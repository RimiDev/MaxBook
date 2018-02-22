//package com.rimidev.prerenderview;
//
//import com.rimidev.backing.LoginBackingBean;
//import com.rimidev.maxbook.beans.Client;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.logging.Logger;
//
//import javax.enterprise.context.RequestScoped;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.servlet.http.Cookie;

/**
 * This class contains methods to read and write cookies Cookies can be read at
 * any time but can only be written to before any HTML is delivered to the
 * browser. For that reason creating cookies is always a preRenderView type
 * event
 *
 * @author Ken
 */
//@Named
//@RequestScoped
//public class PreRenderViewBean {
//
//    @Inject
//    LoginBackingBean loginBean;
//
//    // Default logger is java.util.logging
//    private static final Logger LOG = Logger.getLogger("PreRenderViewBean.class");
//
//    /**
//     * Look for a cookie
//     */
//    public void checkCookies() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        Map<String, Object> cookieMap = context.getExternalContext().getRequestCookieMap();
//
//        // Retrieve all cookies
//        if (cookieMap == null || cookieMap.isEmpty()) {
//            LOG.info("No cookies");
//        } else {
//            ArrayList<Object> ac = new ArrayList<>(cookieMap.values());
//
//            // Streams coding to print out the contenst of the cookies found
//            ac.stream().map((c) -> {
//                LOG.info(((Cookie) c).getName());
//                return c;
//            }).forEach((c) -> {
//                LOG.info(((Cookie) c).getValue());
//            });
//        }
//
//        // Retrieve a specific cookie
//        Object my_cookie = context.getExternalContext().getRequestCookieMap().get("KenCookie");
//        if (my_cookie != null) {
//            LOG.info(((Cookie) my_cookie).getName());
//            LOG.info(((Cookie) my_cookie).getValue());
//        }
//        writeCookie();
//    }
//
//    /**
//     * Let's write a cookie!
//     * http://docs.oracle.com/javaee/7/api/javax/faces/context/ExternalContext.html#addResponseCookie(java.lang.String,
//     * java.lang.String, java.util.Map)
//     */
//    public void writeCookie() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.getExternalContext().addResponseCookie("clientCookie", loginBean.getEmail(), null);
//    }

//}
