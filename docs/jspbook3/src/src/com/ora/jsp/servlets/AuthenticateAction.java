package com.ora.jsp.servlets;

import java.io.*;
import java.net.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.ora.jsp.beans.emp.*;
import org.apache.struts.action.*;

/**
 * This class performs authentication in the Project Billboard
 * application.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class AuthenticateAction extends Action {

    /**
     * Autheticates a user with help from the EmployeeRegistryBean,
     * using the "userName" and "password" request parameters.
     * If the user can be authenticated, the "validUser" session 
     * attribute is set to an instance of the EmployeeBean, to
     * serve as an authentication token in this application.
     * <p>
     * Cookies with the user name and password are set or reset
     * as specified by the "remember" request parameter.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward perform(ActionMapping mapping, 
        ActionForm form, HttpServletRequest request, 
	HttpServletResponse response) throws IOException, ServletException {

	String userName = request.getParameter("userName");
        String password = request.getParameter("password");

	ActionForward nextPage = mapping.findForward("main");

	EmployeeBean emp = null;
        try {
	    EmployeeRegistryBean empReg = (EmployeeRegistryBean) 
		getServlet().getServletContext().getAttribute("empReg");
            emp = empReg.authenticate(userName, password);
        }
        catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
	if (emp != null) {
	    // Valid login
	    HttpSession session = request.getSession();
	    session.setAttribute("validUser", emp);
	    setLoginCookies(request, response, userName, password);
                
	    // Next page is the originally requested URL or main
	    String next = request.getParameter("origURL");
	    if (next != null && next.length() != 0) {
		nextPage = new ActionForward(next, true);
	    }
	}
	else {
	    // Invalid login. Redirect to the login page
	    String loginPage = mapping.findForward("login").getPath();
	    String loginURL = loginPage + 
		"?errorMsg=Invalid+User+Name+or+Password";

	    /*
	     * Create a new ActionForward for the login page with
	     * the parameters.
	     */
	    nextPage = new ActionForward(loginURL, true);
	}
	return nextPage;
    }

    /**
     * Set or "delete" the login cookies, depending on the value of the
     * "remember" parameter.
     */
    private void setLoginCookies(HttpServletRequest request,
	HttpServletResponse response, String userName, String password) {

	Cookie userNameCookie = new Cookie("userName", userName);
	Cookie passwordCookie = new Cookie("password", password);
	// Cookie age in seconds: 30 days * 24 hours * 60 minutes * 60 seconds
	int maxAge = 30 * 24 * 60 * 60;
	if (request.getParameter("remember") == null) {
	    // maxAge = 0 to delete the cookie
	    maxAge = 0;
	}
	userNameCookie.setMaxAge(maxAge);
	passwordCookie.setMaxAge(maxAge);
	userNameCookie.setPath(request.getContextPath());
	passwordCookie.setPath(request.getContextPath());
	response.addCookie(userNameCookie);
	response.addCookie(passwordCookie);
    }
}
