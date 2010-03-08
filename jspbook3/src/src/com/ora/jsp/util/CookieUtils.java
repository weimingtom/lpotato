package com.ora.jsp.util;

import javax.servlet.http.*;

/**
 * This class contains a number of static methods that can be used to
 * work with javax.servlet.Cookie objects.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class CookieUtils {
    
    /**
     * Returns the value of the Cookie with the specified name,
     * or null if not found.
     */
    public static String getCookieValue(String name, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
	if (cookies == null) {
	    return null;
	}

        String value = null;
	for (int i = 0; i < cookies.length; i++) {
	    if (cookies[i].getName().equals(name)) {
		value = cookies[i].getValue();
		break;
	    }
	}
        return value;
    }
    
    /**
     * Creates a Cookie with the specified name, value and max age,
     * and adds it to the response.
     */
    public static void sendCookie(String name, String value, int maxAge,
        HttpServletResponse res) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        res.addCookie(cookie);
    }
 
    /**
     * Returns true if a cookie with the specified name is
     * present in the request.
     */
    public static boolean isCookieSet(String name, HttpServletRequest req) {
        return getCookieValue(name, req) != null;
    }
}
