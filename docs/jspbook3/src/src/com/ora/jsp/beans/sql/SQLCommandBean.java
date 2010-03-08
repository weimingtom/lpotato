package com.ora.jsp.beans.sql;

import java.util.*;
import java.sql.*;
import javax.servlet.jsp.jstl.sql.*;

/**
 * This class is a bean for executing SQL statements. It has three
 * properties that can be set: connection, sqlValue and values.
 * The connection and sqlValue properties must always be set before
 * calling one of the execute methods. If the values property is
 * set, the sqlValue property must be an SQL statement with question
 * marks as placeholders for the value objects in the values
 * property.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class SQLCommandBean {
    private Connection conn;
    private String sqlValue;
    private List values;

    /**
     * Sets the Connection to use.
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Set the SQL string, possibly with question mark placeholders for
     * values set by setValues().
     */
    public void setSqlValue(String sqlValue) {
        this.sqlValue = sqlValue;
    }
    
    /**
     * Sets the values to use for the place holders in the SQL
     * string. The List must contain one Object for each place holder, 
     * suitable for use with the PreparedStatement setObject() method.
     */
    public void setValues(List values) {
        this.values = values;
    }
    
    /**
     * Executes the specified SQL string as a query and returns
     * a Result object
     *
     * @return a javax.servlet.jsp.jstl.sql.Result object
     * @exception SQLException
     */
    public Result executeQuery() throws SQLException {
        Result result = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        try {
            if (values != null && values.size() > 0) {
                // Use a PreparedStatement and set all values
                pstmt = conn.prepareStatement(sqlValue);
                setValues(pstmt, values);
                rs = pstmt.executeQuery();
            }
            else {
                // Use a regular Statement
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sqlValue);
            }
            result = ResultSupport.toResult(rs);
        }
        finally {
            if (rs != null) {
                try {rs.close();} catch (SQLException e) {}
            }
            if (stmt != null) {
                try {stmt.close();} catch (SQLException e) {}
            }
            if (pstmt != null) {
                try {pstmt.close();} catch (SQLException e) {}
            }
        }
        return result;
    }

    /**
     * Executes the specified SQL string (any statement except SELECT, such
     * as UPDATE, INSERT, DELETE or CREATE TABLE) and returns
     * the number of rows affected by the statement, or 0 if none.
     *
     * @return the number of rows affected
     * @exception SQLException
     */
    public int executeUpdate() throws SQLException {
        int noOfRows = 0;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        try {
            if (values != null && values.size() > 0) {
                // Use a PreparedStatement and set all values
                pstmt = conn.prepareStatement(sqlValue);
                setValues(pstmt, values);
                noOfRows = pstmt.executeUpdate();
            }
            else {
                // Use a regular Statement
                stmt = conn.createStatement();
                noOfRows = stmt.executeUpdate(sqlValue);
            }
        }
        finally {
            if (rs != null) {
                try {rs.close();} catch (SQLException e) {}
            }
            if (stmt != null) {
                try {stmt.close();} catch (SQLException e) {}
            }
            if (pstmt != null) {
                try {pstmt.close();} catch (SQLException e) {}
            }
        }
        return noOfRows;
    }

    /**
     * Calls setObject() method on the PreparedStatement for all
     * objects in the values List.
     *
     * @param pstmt the PreparedStatement
     * @param values a List with objects
     * @exception SQLException
     */
    private void setValues(PreparedStatement pstmt, List values)
        throws SQLException {
        for (int i = 0; i < values.size(); i++) {
	    Object v = values.get(i);
	    // Set the value using the method corresponding to the type.
	    // Note! Set methods are indexed from 1, so we add 1 to i
	    pstmt.setObject(i + 1, v);
        }
    }
}
