package com.ora.jsp.tags;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.ora.jsp.util.StringFormat;

/**
 * This class is a custom action for conditionally inserting HTML links in a 
 * navigation menu. 
 * <p>
 * If the action is used in a page requested with a URL corresponding
 * to the <code>page</code> attribute, only the HTML text is included. 
 * Otherwise an HTML link (<code>&lt;a&gt;...&lt;/a&gt;</code>) element 
 * is used to enclose the HTML text. The action also "URL rewrites" the page 
 * URL (embeds a session ID, if needed).
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 3.0
 */
public class MenuItemTag extends SimpleTagSupport {
    private String page;

    /**
     * Sets the page attribute.
     * 
     * @param page the page URI value
     */
    public void setPage(String page) {
        this.page = page;
    }
    
    /**
     * Writes either the body content as-is or enclosed in an HTML link
     * element to the current JspWriter, depending on if the request URI
     * matches the <code>page</code> attribute value or not.
     * The content is enclosed in an HTML link element 
     * (<code>&lt;a&gt;...&lt;/a&gt;</code>) if the <code>page</code> 
     * attribute doesn't correspond to the current page and the link
     * is "URL rewritten" (a session ID is added, if needed), and the 
     * result is written to the current JspWriter.
     */
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
	    throw new JspTagException("'menuItem' used without a body");
	}
	
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = 
            (HttpServletRequest) pageContext.getRequest();
        String requestURI = request.getServletPath();
        // Convert the specified page URI to a context-relative URI
        String pageURI = StringFormat.toContextRelativeURI(page, requestURI);

        if (requestURI.equals(pageURI)) {
            // Add the body as-is
            body.invoke(null);
        }
        else {
            // Add the body as the text of an HTML link to page
            String uri = request.getContextPath() + pageURI;
            HttpServletResponse response = 
                (HttpServletResponse) pageContext.getResponse();

            StringWriter evalResult = new StringWriter();
            StringBuffer buff = evalResult.getBuffer();
            buff.append("<a href=\"").append(response.encodeURL(uri)).
                append("\">");
            body.invoke(evalResult);
            buff.append("</a>");
            getJspContext().getOut().print(buff);
        }
    }
}
