package com.ora.jsp.tags;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.ora.jsp.util.*;

/**
 * This class is a custom action for adding a cookie header
 * to the response.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 3.0
 */
public class AddCookieTag extends SimpleTagSupport {
    private String name;
    private String value;
    private String maxAgeString;

    /**
     * Sets the cookie name attribute.
     *
     * @param name the name of the cookie
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the cookie value attribute.
     *
     * @param value the value of the cookie
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the cookie maxAge attribute.
     *
     * @param maxAgeString the max age (in seconds) of the cookie
     */
    public void setMaxAge(String maxAgeString) {
        this.maxAgeString = maxAgeString;
    }

    /**
     * Creates a cookie and adds it to the response
     */
    public void doTag() throws JspException {
        int maxAge = -1;
        if (maxAgeString != null) {
	    try {
		maxAge = Integer.valueOf(maxAgeString).intValue();
	    }
	    catch (NumberFormatException e) {
		throw new JspTagException("Invalid maxAge", e);
	    }
        }
	PageContext pageContext = (PageContext) getJspContext();
	HttpServletResponse response = 
	    (HttpServletResponse) pageContext.getResponse();
        CookieUtils.sendCookie(name, value, maxAge, response);
    }
}
