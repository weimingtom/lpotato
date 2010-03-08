package com.ora.jsp.tags.xmp;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class AbortPageTag extends SimpleTagSupport {
    public void doTag() throws JspException {
	throw new SkipPageException();
    }
}
