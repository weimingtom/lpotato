package com.ora.jsp.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.jstl.core.*;
import org.apache.taglibs.standard.lang.support.*;
import com.ora.jsp.util.StringFormat;

/**
 * This class is a custom action for testing if a string value
 * is in the format of a valid SMTP email address.
 * If it is, the body is evaluated. If a var attribute is provided, 
 * the result is also saved as a Boolean variable in the specified 
 * scope, or in the page if no scope is specified.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 3.0
 */
public class IfValidEmailAddrTag extends ConditionalTagSupport {
    private String value;

    public void setValue(String value) {
	this.value = value;
    }

    public boolean condition() throws JspTagException {
	return StringFormat.isValidEmailAddr(value);
    }
}
