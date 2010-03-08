package com.ora.jsp.tags.xmp;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class SimpleLoopTag extends SimpleTagSupport {
    private Collection items;
    private String var;
    
    public void setItems(Collection items) {
        this.items = items;
    }
    
    public void setVar(String var) {
        this.var = var;
    }

    public void doTag() throws JspException, IOException {
	JspFragment body = getJspBody();
	if (body != null) {
	    Iterator i = items.iterator();
	    while (i.hasNext()) {
		Object currValue = i.next();
		getJspContext().setAttribute(var, currValue);
		body.invoke(null);
	    }
	}
    }
}
