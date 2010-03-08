package com.ora.jsp.tags.xmp;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.ora.jsp.util.*;
import org.apache.taglibs.standard.lang.support.*;

/**
 * This class is a custom action for adding a cookie header
 * to the response.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class ClassicAddCookieTag extends TagSupport {
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
    public int doEndTag() throws JspException {
        int maxAge = -1;
        if (maxAgeString != null) {
	    try {
		maxAge = Integer.valueOf(maxAgeString).intValue();
	    }
	    catch (NumberFormatException e) {
		throw new JspTagException("Invalid maxAge", e);
	    }
        }
        CookieUtils.sendCookie(name, value, maxAge,
            (HttpServletResponse) pageContext.getResponse());
        return EVAL_PAGE;
    }
    
    /**
     * Releases all instance variables.
     */
    public void release() {
        name = null;
        value = null;
        maxAgeString = null;
        super.release();
    }
}
