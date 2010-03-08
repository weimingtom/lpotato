package com.ora.jsp.sql;

import java.sql.*;
import java.util.*;

/**
 * This class is a wrapper around a Connection, overriding the
 * close method to just inform the DataSourceWrapper that it's available 
 * for reuse again, and the isClosed method to return the state
 * of the wrapper instead of the Connection.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class ConnectionWrapper implements Connection {
    private Connection realConn;
    private DataSourceWrapper dsw;
    private boolean isClosed = false;

    public ConnectionWrapper(Connection realConn, DataSourceWrapper dsw) {
        this.realConn = realConn;
        this.dsw = dsw;
    }

    /**
     * Inform the DataSourceWrapper that the ConnectionWrapper
     * is closed.
     */
    public void close() throws SQLException {
        isClosed = true;
        dsw.returnConnection(realConn);
    }

    /**
     * Returns true if the ConnectionWrapper is closed, false
     * otherwise.
     */
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    /*
     * Wrapped methods. See Connection for details.
     */
    
    public void clearWarnings() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.clearWarnings();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void commit() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.commit();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public Statement createStatement() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createStatement();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public Statement createStatement(int resultSetType,
        int resultSetConcurrency) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createStatement(resultSetType, resultSetConcurrency);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public Statement createStatement(int resultSetType,
        int resultSetConcurrency, int resultSetHoldability) 
	throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createStatement(resultSetType, resultSetConcurrency,
	    resultSetHoldability);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public boolean getAutoCommit() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getAutoCommit();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public String getCatalog() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getCatalog();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public int getHoldability() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getHoldability();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getMetaData();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public int getTransactionIsolation() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getTransactionIsolation();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public Map getTypeMap() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getTypeMap();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public SQLWarning getWarnings() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getWarnings();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public boolean isReadOnly() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.isReadOnly();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public String nativeSQL(String sql) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.nativeSQL(sql);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public CallableStatement prepareCall(String sql) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareCall(sql);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public CallableStatement prepareCall(String sql,int resultSetType,
        int resultSetConcurrency) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public CallableStatement prepareCall(String sql,int resultSetType,
        int resultSetConcurrency, int resultSetHoldability) 
	throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareCall(sql, resultSetType, resultSetConcurrency,
            resultSetHoldability);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public PreparedStatement prepareStatement(String sql,
	int autoGeneratedKeys) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, autoGeneratedKeys);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public PreparedStatement prepareStatement(String sql,
	int[] columnIndexes) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, columnIndexes);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public PreparedStatement prepareStatement(String sql,
	String[] columnNames) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, columnNames);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType,
        int resultSetConcurrency) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, resultSetType, 
	    resultSetConcurrency);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType,
        int resultSetConcurrency, int resultSetHoldability) 
	throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, resultSetType, 
	    resultSetConcurrency, resultSetHoldability);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.releaseSavepoint(savepoint);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void rollback() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.rollback();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void rollback(Savepoint savepoint) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.rollback(savepoint);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setAutoCommit(autoCommit);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void setCatalog(String catalog) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setCatalog(catalog);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void setHoldability(int holdability) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setHoldability(holdability);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void setReadOnly(boolean readOnly) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setReadOnly(readOnly);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public Savepoint setSavepoint() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.setSavepoint();
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public Savepoint setSavepoint(String name) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.setSavepoint(name);
    }

    /**
     * Calls the corresponding method on the wrapped Connection.
     */
    public void setTransactionIsolation(int level) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setTransactionIsolation(level);
    }

    public void setTypeMap(Map map) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setTypeMap(map);
    }
}
