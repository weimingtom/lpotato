package com.ora.jsp.servlets;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.ora.jsp.beans.emp.*;
import org.apache.struts.action.*;

/**
 * This class updates a user profile in the Project Billboard
 * application.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class UpdateProfileAction extends Action {

    /**
     * Updates the projects property of an authenticated user,
     * represented by the "validUser" session attribute, using
     * the EmployeeRegistryBean. This action is only performed
     * for POST requests. Before returning, the client is
     * redirected to the main page, where the new set of projects
     * are displayed.
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

        if (request.getMethod().equals("POST")) {
            String[] projects = request.getParameterValues("projects");
            if (projects == null) {
                projects = new String[0];
            }
            HttpSession session = request.getSession();
            EmployeeBean emp = 
                (EmployeeBean) session.getAttribute("validUser");
            emp.setProjects(projects);
            EmployeeRegistryBean empReg = (EmployeeRegistryBean) 
                getServlet().getServletContext().getAttribute("empReg");
            try {
                empReg.saveEmployee(emp);
            }
            catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        }
	ActionForward nextPage = mapping.findForward("main");
        return nextPage;
    }
}
