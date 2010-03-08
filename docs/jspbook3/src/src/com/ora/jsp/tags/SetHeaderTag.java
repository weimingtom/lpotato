package com.ora.jsp.tags;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.lang.support.*;

/**
 * This class is a custom action for setting response header
 * values.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class SetHeaderTag extends SimpleTagSupport {
    private String name;
    private String value;

    /**
     * Sets the name attribute.
     *
     * @param name the parameter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value attribute from a String.
     *
     * @param value the parameter String value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the header specified by the name property to the value
     * specified by the value property.
     */
    public void doTag() throws JspException {
        if (value.length() == 0 || name.length() == 0) {
            throw new JspTagException("setHeader: " +
                 "one of the attributes is not set");
        }
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletResponse response = 
            (HttpServletResponse) pageContext.getResponse();
        response.setHeader(name, value);
    }
}
