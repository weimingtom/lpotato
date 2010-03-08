package com.ora.jsp.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

/**
 * This class performs the log out from the Project Billboard
 * application.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class LogoutAction extends Action {
    /**
     * Invalidates the session, thereby removing the authentication
     * token, and redirects to the login page.
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

	/**
	 * Invalidate the session, if any. Use false to avoid creating
	 * a new one if it has already timed out.
	 */
        HttpSession session = request.getSession(false);
	if (session != null) {
	    session.invalidate();
	}
	ActionForward nextPage = mapping.findForward("login");
        return nextPage;
    }
}
