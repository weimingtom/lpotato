package com.ora.jsp.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import oracle.jdbc.pool.*;

/**
 * This class manages the DataSource resource for a fictious
 * application, creating an Oracle DataSource with pooling capabilities
 * and makes it available when the application starts and removes it 
 * when the application is shut down.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class ResourceManagerListener2 implements ServletContextListener {
    private OracleConnectionCacheImpl ds = null;

    public void contextInitialized(ServletContextEvent sce) {

        ServletContext application  = sce.getServletContext();

        /*
         * Get the JDBC URL, user, password and limit from the web.xml
         * context init parameters
         */
        String jdbcURL = application.getInitParameter("jdbcURL");
        String user = application.getInitParameter("user");
        String password = application.getInitParameter("password");
        String maxLimit = application.getInitParameter("maxLimit");

        try {
            ds = new OracleConnectionCacheImpl();
            ds.setURL(jdbcURL);
            ds.setMaxLimit(Integer.parseInt(maxLimit));
            ds.setUser("scott");
            ds.setPassword("tiger");
        }
        catch (Exception e) {
            application.log("Failed to create data source: " + e.getMessage());
        }
        application.setAttribute("appDataSource", ds);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext application  = sce.getServletContext();
        application.removeAttribute("appDataSource");
        // Close the connections in the DataSource
        try {
            ds.close();
        }
        catch (java.sql.SQLException e) {}
	ds = null;
    }
}
