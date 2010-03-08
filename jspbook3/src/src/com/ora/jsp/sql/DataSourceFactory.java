package com.ora.jsp.sql;

import java.beans.*;
import java.lang.reflect.*;
import java.util.*;
import javax.naming.*;
import javax.naming.spi.ObjectFactory;

/**
 * This class is an implementation of the ObjectFactory interface,
 * used by a container to create instances of resources accessible
 * through JNDI. It's intended to be used to create an instance of
 * the DataSource class and configure it as defined by the parameters
 * passed through the JNDI implementation.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class DataSourceFactory implements ObjectFactory {
    /**
     * Creates an instance of the DataSource based on the
     * configuration info. The class name must be passed as a parameter
     * named "dataSourceClassName". Introspection is used to find
     * and call setter methods in the DataSource corresponding to all
     * other parameters.
     */
    public Object getObjectInstance(Object obj, Name name, 
        Context nameCtx, Hashtable environment)
        throws NamingException {

        Reference ref = (Reference) obj;
        RefAddr ra = ref.get("dataSourceClassName");
        if (ra == null) {
	    throw new NamingException("No class name specified");
        }

        String className = (String) ra.getContent();
        Object ds = null;
        try {
            ds = Class.forName(className).newInstance();
        }
        catch (Exception e) {
	    throw new NamingException("Can't create DataSource: "
                + e.getMessage());
        }

        Enumeration addrs = ref.getAll();
        while (addrs.hasMoreElements()) {
            RefAddr addr = (RefAddr) addrs.nextElement();
            String prop = addr.getType();
            String value = (String) addr.getContent();
            if (!(prop.equals("dataSourceClassName") || prop.equals("scope") ||
                prop.equals("auth") || prop.equals("factory"))) {
                setProperty(prop, value, ds);
            } 
        }
        return ds;
    }

    /**
     * Sets the specified property to the specified value in the
     * specified object.
     */
    private void setProperty(String prop, String value, Object ds) {
        Method setter = getSetter(prop, ds);
        if (setter == null) {
            System.out.println("[DataSourceFactory] Can't find property: "
                + prop + ". Ignored");
            return;
        }
        Object[] args = buildArgs(value, setter);
        try {
            setter.invoke(ds, args);
        }
        catch (Exception e) {
            System.out.println("[DataSourceFactory] Can't set property: "
                + prop + "=" + value + "; " + e.getMessage() + ". Ignored");
        }
    }

    /**
     * Returns the setter method corresponding to the property name in
     * the specified object, or null if no matching setter method is
     * found.
     */
    private Method getSetter(String prop, Object ds) {
        BeanInfo bi = null;
        try {
            bi = Introspector.getBeanInfo(ds.getClass());
        }
        catch (IntrospectionException e) {
            return null;
        }

        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        Method setter = null;
        for (int i = 0; i < pds.length; i++) {
            if (pds[i].getName().equals(prop)) {
                setter = pds[i].getWriteMethod();
                break;
            }
        }
        return setter;
    }

    /**
     * Converts the specified String value to the setter methods parameter
     * type, if possible, and returns it as an Object array suitable as
     * the argument to the Method.invoke() method.
     */
    private Object[] buildArgs(String value, Method setter) {
        Class[] paramTypes = setter.getParameterTypes();
        // Only one parameter for a setter method
        Object[] args = new Object[1];
        args[0] = coerceValue(value, paramTypes[0]);
        return args;
    }

    /**
     * Converts the specified String value to the specified type, if
     * possible, and returns the converted value.
     *
     * Only conversion to Integer and Boolean is currently supported,
     * since these types covers all DataSource implementations I've
     * come across. It's easy to add new conversions if needed.
     */
    private Object coerceValue(String value, Class type) {
        Object coercedValue = null;
        if (type.isAssignableFrom(String.class)) {
            coercedValue = value;
        }
        else if (type.isAssignableFrom(Integer.class) ||
                 type.isAssignableFrom(Integer.TYPE)) {
            try {
                coercedValue = Integer.valueOf(value);
            } 
            catch (NumberFormatException e) {
                // Ignore. It's reported when trying to set the value
            }
        }
        else if (type.isAssignableFrom(Boolean.class) ||
                 type.isAssignableFrom(Boolean.TYPE)) {
            coercedValue = Boolean.valueOf(value);
        }
        return coercedValue;
    }
}
