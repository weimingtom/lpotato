package com.ora.jsp.tags.xmp;

import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ClassicSimpleLoopTag extends TagSupport {
    private Iterator iterator;
    private Collection items;
    private String var;
    
    public void setItems(Collection items) {
        this.items = items;
    }
    
    public void setVar(String var) {
        this.var = var;
    }

    public int doStartTag() throws JspException {
	iterator = items.iterator();
        if (iterator.hasNext()) {
            pageContext.setAttribute(var, iterator.next());
	    return EVAL_BODY_INCLUDE;
        }
        else {
	    return SKIP_BODY;
        }
    }

    /**
     * Makes the next element available to the body in a variable
     * with the name specified by the loopId attribute, or returns
     * SKIP_BODY if all elements have been processed.
     */
    public int doAfterBody() throws JspException {
        if (iterator.hasNext()) {
            pageContext.setAttribute(var, iterator.next());
	    return EVAL_BODY_AGAIN;
        }
        else {
	    return SKIP_BODY;
        }
    }

    /**
     * Releases all instance variables.
     */
    public void release() {
	iterator = null;
	items = null;
	var = null;
        super.release();
    }
}
