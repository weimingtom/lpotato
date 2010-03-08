package com.ora.jsp.tags.xmp;

import java.io.*;
import java.util.*;
import java.lang.reflect.Array;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.jstl.core.*;
import org.apache.taglibs.standard.lang.support.*;
import com.ora.jsp.util.StringFormat;

/**
 * This class is a custom action for creating an HTML checkbox
 * control, using status information exposed by the JSTL forEach 
 * action.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class BuildCheckboxTag extends TagSupport {
    private String name;
    private String[] selections;

    public void setName(String name) {
        this.name = name;
    }

    public void setSelections(String[] selections) {
        this.selections = selections;
    }

    public int doEndTag() throws JspException {
        LoopTag parent = (LoopTag) findAncestorWithClass(this, LoopTag.class);
        if (parent == null) {
            throw new JspTagException("buildCheckbox: invalid parent");
        }

        Map.Entry current = (Map.Entry) parent.getCurrent();
        String text = (String) current.getKey();
        String value = (String) current.getValue();
        JspWriter out = pageContext.getOut();
        StringBuffer checkbox = new StringBuffer("<input type=\"checkbox\"");
        checkbox.append(" name=\"").append(name).append("\"").
            append(" value=\"").append(value).append("\"");
        if (isSelected(value, selections)) {
            checkbox.append(" checked");
        }
        checkbox.append(">").append(text);
        try {
            out.write(checkbox.toString());
        }
        catch (IOException e) {}
        return EVAL_PAGE;
    }

    private boolean isSelected(String value, String[] selections) {
        return StringFormat.isValidString(value, selections, false); 
    }
}
