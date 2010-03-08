package com.ora.jsp.tags.xmp;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class IfTag extends SimpleTagSupport {
    private boolean test;

    public void setTest(boolean test) {
	this.test = test;
    }

    public void doTag() throws JspException, IOException {
	if (test && getJspBody() != null) {
	    getJspBody().invoke(null);
	}
    }
}
