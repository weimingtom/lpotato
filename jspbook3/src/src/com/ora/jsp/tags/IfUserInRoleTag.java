package com.ora.jsp.tags;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.jstl.core.*;
import org.apache.taglibs.standard.lang.support.*;

/**
 * This class is a custom action for testing if the current (authenticated)
 * user belongs to the specified security role.
 * If he/she is, the body is evaluated. If a var attribute is provided, 
 * the result is also saved as a Boolean variable in the specified 
 * scope, or in the page if no scope is specified.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 3.0
 */
public class IfUserInRoleTag extends ConditionalTagSupport {
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public boolean condition() throws JspTagException {
        HttpServletRequest request = 
            (HttpServletRequest) pageContext.getRequest();
        return request.isUserInRole(value);
    }
}
