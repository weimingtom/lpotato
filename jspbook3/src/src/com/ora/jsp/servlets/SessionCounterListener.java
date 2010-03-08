package com.ora.jsp.servlets;

import javax.servlet.*; 
import javax.servlet.http.*; 

/**
 * This class manages a counter for the number of active sessions in
 * an application. The counter is made available to the rest of the
 * application as a servlet context attribute of type <code>int[]</code> 
 * with one element.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class SessionCounterListener implements HttpSessionListener { 
    private static final String COUNTER_ATTR = "session_counter";

    /**
     * Increments the counter held in the session scope.
     */
    public void sessionCreated(HttpSessionEvent hse) { 
	int[] counter = getCounter(hse); 
	counter[0]++; 
    } 
    
    /**
     * Decrements the counter held in the session scope.
     */             
    public void sessionDestroyed(HttpSessionEvent hse) { 
	int[] counter = getCounter(hse); 
	counter[0]--; 
    } 
    
    /**
     * Returns the counter held in the session scope, or a new
     * counter if it doesn't exist.
     */             
    private int[] getCounter(HttpSessionEvent hse) { 
	HttpSession session = hse.getSession(); 
	ServletContext context = session.getServletContext(); 
	int[] counter = (int[]) context.getAttribute(COUNTER_ATTR); 
	if (counter == null) { 
	    counter = new int[1]; 
	    context.setAttribute(COUNTER_ATTR, counter); 
	} 
	return counter; 
    } 
}
