package com.ora.jsp.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.ora.jsp.beans.emp.*;
import com.ora.jsp.beans.news.*;
import org.apache.struts.action.*;

/**
 * This class stores a new message in the Project Billboard
 * application.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class StoreMsgAction extends Action {

    /**
     * Creates a new NewsItemBean and sets its properties based
     * on the "category" and "msg" request parameters, plus
     * the firstName and lastName properties of the authenticated
     * user (an EmployeeBean accessible as the "validUser" session
     * attribute). The NewItemBean is then added to the NewsBean.
     * This action is only performed for POST request.
     * Before returning, the client is redirected to the main page, 
     * where the new message is displayed.
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
            String category = request.getParameter("category");
            String msg = request.getParameter("msg");
            if (category == null || msg == null) {
                throw new ServletException("Missing message info");
            }
            HttpSession session = request.getSession();
            EmployeeBean emp = 
                (EmployeeBean) session.getAttribute("validUser");
            NewsItemBean item = new NewsItemBean();
            item.setCategory(category);
            item.setMsg(msg);
            item.setPostedBy(emp.getFirstName() + " " +
                emp.getLastName());
            NewsBean news = (NewsBean)
                servlet.getServletContext().getAttribute("news");
            news.setNewsItem(item);
        }
	ActionForward nextPage = mapping.findForward("main");
        return nextPage;
    }
}
