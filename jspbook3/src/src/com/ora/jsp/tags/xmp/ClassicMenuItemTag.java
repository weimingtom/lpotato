package com.ora.jsp.tags.xmp;

import java.io.*;
import java.util.*;
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
 * @version 2.0
 */
public class ClassicMenuItemTag extends BodyTagSupport {
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
    public int doEndTag() throws JspException {
        HttpServletRequest request =
            (HttpServletRequest) pageContext.getRequest();
        String requestURI = request.getServletPath();
        // Convert the specified page URI to a context-relative URI
        String pageURI = StringFormat.toContextRelativeURI(page, requestURI);

        StringBuffer text = null;
        String body = getBodyContent().getString();
        if (requestURI.equals(pageURI)) {
            text = new StringBuffer(body);
        }
        else {
            // Add the text as an HTML reference if page is not current page
            String contextPath = request.getContextPath();
            String uri = contextPath + pageURI;
            HttpServletResponse res = 
                (HttpServletResponse) pageContext.getResponse();
            text = new StringBuffer("<a href=\"");
            text.append(res.encodeURL(uri)).append("\">").
                append(body).append("</a>");
        }
        try {
            JspWriter out = getPreviousOut();
            out.print(text);
        }
        catch (IOException e) {}
        return EVAL_PAGE;
    }

    /**
     * Releases all instance variables.
     */
    public void release() {
        page = null;
        super.release();
    }
}
