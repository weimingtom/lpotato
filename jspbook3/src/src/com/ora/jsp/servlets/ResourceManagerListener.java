package com.ora.jsp.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import com.ora.jsp.beans.emp.*;
import com.ora.jsp.beans.news.*;
import com.ora.jsp.sql.*;

/**
 * This class manages the resources used by the Project Billboard
 * application, creating them and making them available when the
 * application starts and removing them when the application is
 * shut down.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class ResourceManagerListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application  = sce.getServletContext();

        /*
         * Get the JDBC driver class name and URL from web.xml
         * context init parameters
         */
        String driverClass = application.getInitParameter("driverClass");
        String jdbcURL = application.getInitParameter("jdbcURL");

        DataSourceWrapper ds = null;
        try {
            ds = new DataSourceWrapper();
            ds.setDriverClassName(driverClass);
            ds.setUrl(jdbcURL);
        }
        catch (Exception e) {
            application.log("Error creating connection pool: ", e);
        } 
        EmployeeRegistryBean empReg = new EmployeeRegistryBean();
        empReg.setDataSource(ds);
        application.setAttribute("empReg", empReg);

        NewsBean news = new NewsBean();
        application.setAttribute("news", news);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext application  = sce.getServletContext();
        application.removeAttribute("empReg");
        application.removeAttribute("news");
    }
}
