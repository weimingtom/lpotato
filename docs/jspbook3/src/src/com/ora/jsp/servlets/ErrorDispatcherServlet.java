package com.ora.jsp.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * This class can be registered as an error handler for exceptions
 * and status codes in the web application deployment description.
 * It forwards to a JSP error page after setting all request
 * attributes the JSP container uses to initialize the exception
 * variable, as well as an attribute to contain the URI for the
 * request that triggered the error handler.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class ErrorDispatcherServlet extends HttpServlet {
    private static final String SERVLET_EXCEPTION =
        "javax.servlet.error.exception";
    private static final String JSP_EXCEPTION =
        "javax.servlet.jsp.jspException";
    private static final String SERVLET_ERROR_SOURCE =
        "javax.servlet.error.request_uri";
    private static final String JSP_ERROR_SOURCE =
        "sourcePage";
    private static final String HTTP_STATUS_CODE =
        "javax.servlet.error.status_code";

    private String errorPage;

    /**
     * Reads the "errorPage" init parameter and saves the value in an
     * instance variable. The value must be a context-relative path
     * to a JSP error page.
     *
     * @exception UnavailableException if "errorPage" is not set.
     */
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        errorPage = config.getInitParameter("errorPage");
        if (errorPage == null) {
            throw new UnavailableException("errorPage not defined");
        }
        getServletContext().log("errorPage: " + errorPage);
    }

    /**
     * Calls doPost()
     */
    public void doGet(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    /**
     * Sets the request attributes for the exeception and request URI
     * and forwards to the JSP error page.
     */
    public void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

        /*
         * Try to get the servlet exception and save it in the
         * attribute expected by a JSP error page if found
         */
        Object t = request.getAttribute(SERVLET_EXCEPTION);
        if (t != null) {
            request.setAttribute(JSP_EXCEPTION, t);
        }
        else {
            /* 
             * If there's a status code, create an Exception with the
             * status code so the JSP error page can get it.
             */
            Object statusCode = request.getAttribute(HTTP_STATUS_CODE);
            if (statusCode != null) {
                request.setAttribute(JSP_EXCEPTION, 
                    new Exception("HTTP status code: " + statusCode));
            }
        }

        /*
         * The URI of the page causing the problem is passed as
         * a request attribute for servlets. If it's there, save
         * it in the request attribute the JSP page uses instead.
         */
        Object errorSource = request.getAttribute(SERVLET_ERROR_SOURCE);
        if (errorSource != null) {
            request.setAttribute(JSP_ERROR_SOURCE, errorSource);
        }

        ServletContext context = getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher(errorPage);
        rd.forward(request, response);
    }
}
