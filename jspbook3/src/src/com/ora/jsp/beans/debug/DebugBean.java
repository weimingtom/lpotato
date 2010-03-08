package com.ora.jsp.beans.debug;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

/**
 * This class is a bean that can be used to extract debug
 * information from a JSP PageContext. 
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class DebugBean {
    private PageContext pageContext;
    private String debugType;
    private ServletContext context;
    
    /**
     * Sets the pageContext property.
     */
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }
    
    /**
     * Returns a Map with all basic request information.
     */
    public Map getRequestInfo() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        info.put("authType", nullToString(req.getAuthType()));
        info.put("characterEncoding", nullToString(req.getCharacterEncoding()));
        info.put("contentLength", new Integer(req.getContentLength()).toString());
        info.put("contentType", nullToString(req.getContentType()));
        info.put("contextPath", nullToString(req.getContextPath()));
        info.put("pathInfo", nullToString(req.getPathInfo()));
        info.put("protocol", nullToString(req.getProtocol()));
        info.put("queryString", nullToString(req.getQueryString()));
        info.put("remoteAddr", nullToString(req.getRemoteAddr()));
        info.put("remoteHost", nullToString(req.getRemoteHost()));
        info.put("remoteUser", nullToString(req.getRemoteUser()));
        info.put("requestURI", nullToString(req.getRequestURI()));
        info.put("scheme", nullToString(req.getScheme()));
        info.put("serverName", nullToString(req.getServerName()));
        info.put("serverPort", new Integer(req.getServerPort()).toString());
        info.put("servletPath", nullToString(req.getServletPath()));
        return info;
    }
    
    /**
     * Returns a Map with all header information.
     */
    public Map getHeaders() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        Enumeration names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Enumeration values = req.getHeaders(name);
            StringBuffer sb = new StringBuffer();
            boolean first = true;
            while (values.hasMoreElements()) {
                if (!first) {
                    sb.append(" | ");
                }
                first = false;
                sb.append(values.nextElement());
            }
            info.put(name, sb.toString());
        }
        return info;
    }
    
    /**
     * Returns a Map with all cookie information.
     */
    public Map getCookies() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        Cookie[] cookies = req.getCookies();
	if (cookies != null) {
	    for (int i = 0; i < cookies.length; i++) {
		info.put(cookies[i].getName(), cookies[i].getValue());
	    }
	}
        return info;
    }
    
    /**
     * Returns a Map with all request parameter information.
     */
    public Map getParams() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        Enumeration names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String[] values = req.getParameterValues(name);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < values.length; i++) {
                if (i != 0) {
                    sb.append(" | ");
                }
                sb.append(values[i]);
            }
            info.put(name, sb.toString());
        }
        return info;
    }
    
    /**
     * Returns a Map with all request scope variables.
     */
    public Map getRequestScope() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        Enumeration names = req.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Object value = req.getAttribute(name);
            info.put(name, toStringValue(value));
        }
        return info;
    }
    
    /**
     * Returns a Map with all page scope variables.
     */
    public Map getPageScope() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        Enumeration names = 
            pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Object value = pageContext.getAttribute(name);
            info.put(name, toStringValue(value));
        }
        return info;
    }
    
    /**
     * Returns a Map with all session scope variables.
     */
    public Map getSessionScope() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = req.getSession();
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Object value = session.getAttribute(name);
            info.put(name, toStringValue(value));
        }
        return info;
    }
    
    /**
     * Returns a Map with all application scope variables.
     */
    public Map getApplicationScope() {
        if (pageContext == null) {
            throw new IllegalStateException("The pageContext property is not set");
        }
        
        Map info = new HashMap();
        ServletContext context = pageContext.getServletContext();
        Enumeration names = context.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Object value = context.getAttribute(name);
            info.put(name, toStringValue(value));
        }
        return info;
    }
    
    /**
     * Returns the String "null" if the value is null,
     * otherwise the value itself.
     */
    private String nullToString(String value) {
        if (value == null) {
            return "null";
        }
        else {
            return value;
        }
    }
    
    /**
     * Returns a String representation of the specified
     * Object, in a format suitable for debug output.
     */
    private String toStringValue(Object value) {
        StringBuffer sb = new StringBuffer();
        Class type = value.getClass();
        if (type.isArray()) {
            Class componentType = type.getComponentType();
            sb.append(componentType.getName());
            sb.append("[]: {");
            if (!componentType.isPrimitive()) {
                Object[] arr = (Object[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Boolean.TYPE) {
                boolean[] arr = (boolean[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Byte.TYPE) {
                byte[] arr = (byte[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Character.TYPE) {
                char[] arr = (char[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Double.TYPE) {
                double[] arr = (double[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Float.TYPE) {
                float[] arr = (float[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Integer.TYPE) {
                int[] arr = (int[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Long.TYPE) {
                long[] arr = (long[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            else if (componentType == Short.TYPE) {
                short[] arr = (short[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(arr[i]);
                }
            }
            sb.append("}");
        }
        else {
            sb.append(value.getClass().getName()).
                append(": ").
                append(value.toString());
        }
        return sb.toString();
    }
}
