package com.ora.jsp.sql;

import java.io.*;
import java.sql.*;
import javax.sql.*;

/**
 * This class is a wrapper implementing the JDBC 2.0 DataSource
 * interface, used to make the ConnectionPool class look like
 * a JDBC 2.0 DataSource.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class DataSourceWrapper implements DataSource {
    private ConnectionPool pool;
    private String driverClassName;
    private String url;
    private String user;
    private String password;
    private int initialConnections;

    /**
     * Sets the JDBC driver class name for the pool.
     */
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * Sets the JDBC URL for the pool.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the user account for the pool.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sets the user account password for the pool.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the number of connections to create when the pool is
     * created.
     */
    public void setInitialConnections(int initialConnections) {
        this.initialConnections = initialConnections;
    }

    /**
     * Gets a connection from the pool and returns it wrapped in
     * a ConnectionWrapper.
     */
    public Connection getConnection() throws SQLException {
        if (pool == null) {
            createConnectionPool();
        }
        return new ConnectionWrapper(pool.getConnection(), this);
    }
    
    /**
     * Returns a Connection to the pool. This method is called by
     * the ConnectionWrapper's close() method.
     */
    public void returnConnection(Connection conn) {
        pool.returnConnection(conn);
    }
    
    /**
     * Always throws an SQLException. Username and password are set
     * with the setter methods and can not be changed.
     */
    public Connection getConnection(String username, String password) 
            throws SQLException {
        throw new SQLException("Not supported");
    }
    
    /**
     * Always throws an SQLException. Not supported.
     */
    public int getLoginTimeout() throws SQLException {
        throw new SQLException("Not supported");
    }
    
    /**
     * Always throws an SQLException. Not supported.
     */
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException("Not supported");
    }
    
    /**
     * Always throws an SQLException. Not supported.
     */
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLException("Not supported");
    }
    
    /**
     * Always throws an SQLException. Not supported.
     */
    public synchronized void setLogWriter(PrintWriter out) throws SQLException {
        throw new SQLException("Not supported");
    }

    /**
     * Create a Connection pool based on the configuration properties.
     */
    private void createConnectionPool() throws SQLException {

        try {
            pool = new ConnectionPool(driverClassName, url, user,
                password, initialConnections);
        }
        catch (SQLException e) {
            throw e;
        }
        catch (Exception e) {
            SQLException sqle = 
                new SQLException("Error creating pool: " + 
                    e.getClass().getName() + " : " + e.getMessage());
            throw sqle;
        }
    }
}
