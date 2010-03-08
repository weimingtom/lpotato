package com.ora.jsp.beans.emp;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.sql.*;
import javax.servlet.jsp.jstl.sql.*;
import com.ora.jsp.beans.sql.*;

/**
 * This class is an example of an application specific interface
 * to a database. It contains methods for authenticating a user,
 * and retrieving and updating user information.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class EmployeeRegistryBean implements Serializable {
    private DataSource dataSource;

    /**
     * Sets the dataSource property value.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Returns an EmployeeBean if the specified user name and password
     * match an employee in the database, otherwise null.
     */
    public EmployeeBean authenticate(String userName, String password) 
        throws SQLException {
            
        EmployeeBean empInfo = getEmployee(userName);
        if (empInfo != null && empInfo.getPassword().equals(password)) {
            return empInfo;
        }
        return null;
    }

    /**
     * Returns an EmployeeBean initialized with the information
     * found in the database for the specified employee, or null if
     * not found.
     */
    public EmployeeBean getEmployee(String userName) throws SQLException {
        
        // Get the user info from the database
        Connection conn = dataSource.getConnection();
        Map empRow = null;
        Map[] projects = null;
        try {
            empRow = getSingleValueProps(userName, conn);
            projects = getProjects(userName, conn);
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException e) {} // Ignore
        }

        // Create a EmployeeBean if the user was found
        if (empRow == null) {
            // Not found
            return null;
        }
        
        EmployeeBean empInfo = new EmployeeBean();
        empInfo.setDept((String) empRow.get("Dept"));
        empInfo.setEmpDate((java.util.Date) empRow.get("EmpDate"));
        empInfo.setEmailAddr((String) empRow.get("EmailAddr"));
        empInfo.setFirstName((String) empRow.get("FirstName"));
        empInfo.setLastName((String) empRow.get("LastName"));
        empInfo.setPassword((String) empRow.get("Password"));
        empInfo.setUserName((String) empRow.get("UserName"));
        empInfo.setProjects(toProjectsArray(projects));
        return empInfo;
    }

    /**
     * Inserts the information about the specified employee, or 
     * updates the information if it's already defined.
     */
    public void saveEmployee(EmployeeBean empInfo) throws SQLException {
        
        // Save the user info from the database
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            saveSingleValueProps(empInfo, conn);
            saveProjects(empInfo, conn);
            conn.commit();
        }
        catch (SQLException e) {
            conn.rollback();
        }
        finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            }
            catch (SQLException e) {} // Ignore
        }
    }
    
    /**
     * Returns a Map with all information about the specified
     * employee except the project list, or null if not found.
     */
    private Map getSingleValueProps(String userName, Connection conn) 
        throws SQLException {

        if (userName == null) {
            return null;
        }
        
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM Employee ")
            .append("WHERE UserName = ?");
        sqlCommandBean.setSqlValue(sql.toString());
        List values = new ArrayList();
        values.add(userName);
        sqlCommandBean.setValues(values);
        Result result = sqlCommandBean.executeQuery();
        if (result == null || result.getRowCount() == 0) {
            // User not found
            return null;
        }
        return result.getRows()[0];
    }

    /**
     * Returns a Map[] with a Map per project for the specified employee, 
     * or null if not found.
     */
    private Map[] getProjects(String userName, Connection conn) 
        throws SQLException {

        if (userName == null) {
            return null;
        }
        
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM EmployeeProjects ")
            .append("WHERE UserName = ?");
        sqlCommandBean.setSqlValue(sql.toString());
        List values = new ArrayList();
        values.add(userName);
        sqlCommandBean.setValues(values);
        return sqlCommandBean.executeQuery().getRows();
    }
    
    /**
     * Inserts the information about the specified employee (except projects), 
     * or updates the information if it's already defined.
     */
    private void saveSingleValueProps(EmployeeBean empInfo, Connection conn) 
        throws SQLException {

        if (empInfo == null) {
            return;
        }
        
        StringBuffer sql = new StringBuffer();
        EmployeeBean dbInfo = getEmployee(empInfo.getUserName());
        if (dbInfo == null) {
            // Use INSERT statement
            sql.append("INSERT INTO Employee ").
                append("(Dept, EmpDate, EmailAddr, FirstName, LastName, ").
                append("Password, ModDate, UserName) ").
                append("VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        }
        else {
            // Use UPDATE statement
            sql.append("UPDATE Employee SET Dept = ?, EmpDate = ?, ")
                .append("EmailAddr = ?, FirstName = ?, LastName = ?,")
                .append("Password = ?, ModDate = ? WHERE Username = ?");
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        List values = new ArrayList();
        values.add(empInfo.getDept());
        // Converts java.util.Date to java.sql.Date
        java.sql.Date empDate = 
            new java.sql.Date(empInfo.getEmpDate().getTime());
        values.add(empDate);
        values.add(empInfo.getEmailAddr());
        values.add(empInfo.getFirstName());
        values.add(empInfo.getLastName());
        values.add(empInfo.getPassword());
        values.add(new Timestamp(System.currentTimeMillis()));
        values.add(empInfo.getUserName());
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Updates or inserts project information for the specified
     * employee.
     */
    private void saveProjects(EmployeeBean empInfo, Connection conn) 
        throws SQLException {

        if (empInfo == null) {
            return;
        }
        
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue("DELETE FROM EmployeeProjects WHERE UserName = ?");
        List values = new ArrayList();
        values.add(empInfo.getUserName());
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
        
        String[] projects = empInfo.getProjects();
        sqlCommandBean.setSqlValue("INSERT INTO EmployeeProjects VALUES(?, ?)");
        for (int i = 0; i < projects.length; i++) {
            values.clear();
            values.add(empInfo.getUserName());
            values.add(projects[i]);
            sqlCommandBean.executeUpdate();
        }
    }
    
    /**
     * Converts a Vector with Row elements containing a ProjectName
     * column to a String[] with the project names.
     */
    private String[] toProjectsArray(Map[] projectRows) {
        if (projectRows == null) {
            return new String[0];
        }
        
        String[] arr = new String[projectRows.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (String) projectRows[i].get("ProjectName");
        }
        return arr;
    }
}
