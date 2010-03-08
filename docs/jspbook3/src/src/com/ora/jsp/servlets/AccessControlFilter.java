package com.ora.jsp.servlets;

import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * This class provides access control for all requests in the Project 
 * Billboard application, by looking for the authentication token in
 * the session and forwarding to the login page if not found.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class AccessControlFilter implements Filter {

    private FilterConfig config = null;
    private String loginPage;

    /**
     * Reads the "loginPage" filter init parameter and saves the
     * value in an instance variable. 
     *
     * @exception ServletException if the "loginPage" parameter is
     *   not set.
     */
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        loginPage = config.getInitParameter("loginPage");
        if (loginPage == null) {
            throw new ServletException("loginPage init parameter missing");
        }
    }

    /**
     * Resets the instance variable.
     */
    public void destroy() {
        config = null;
    }

    /**
     * Looks for the authentication token in the session and forwards
     * to the login page if not found.
     */
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        if (!isAuthenticated(httpReq)) {
            String forwardURI = getForwardURI(httpReq);

            // Forward to the login page and stop further processing
            ServletContext context = config.getServletContext();
            RequestDispatcher rd = context.getRequestDispatcher(forwardURI);
            if (rd == null) {
                httpResp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Login page doesn't exist");
            }
            rd.forward(request, response);
            return;
        }

        /*
         * Process the rest of the filter chain, if any, and ultimately
         * the requested servlet or JSP page.
         */
        chain.doFilter(request, response);
    }

    /**
     * Returns true if the session contains the authentication token.
     */
    private boolean isAuthenticated(HttpServletRequest request) {
        boolean isAuthenticated = false;
        HttpSession session = request.getSession();
        if (session.getAttribute("validUser") != null) {
            isAuthenticated = true;
        }
        return isAuthenticated;
    }

    /**
     * Returns the context-relative path to the login page, with the
     * parameters used by the login page.
     */
    private String getForwardURI(HttpServletRequest request) {
        StringBuffer uri = new StringBuffer(loginPage);
        uri.append("?errorMsg=Please+log+in+first&origURL=").
            append(URLEncoder.encode(getContextRelativeURI(request)));
        return uri.toString();
    }

    /**
     * Returns a context-relative path for the request, including
     * the query string, if any.
     */
    private String getContextRelativeURI(HttpServletRequest request) {
        int ctxPathLength = request.getContextPath().length();
        String requestURI = request.getRequestURI();
        StringBuffer uri = 
            new StringBuffer(requestURI.substring(ctxPathLength));
        String query = request.getQueryString();
        if (query != null) {
            uri.append("?").append(query);
        }
        return uri.toString();
    }
}
