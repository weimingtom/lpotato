package com.ora.jsp.tags.xmp;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.ora.jsp.beans.motd.*;
public class MixedMessageTag extends SimpleTagSupport {
    private MixedMessageBean mmb = new MixedMessageBean();

    // Attributes
    private String category;

    public void setCategory(String category) {
        this.category = category;
    }

    public void doTag() throws IOException {
        mmb.setCategory(category);
	JspWriter out = getJspContext().getOut();
	out.println(mmb.getMessage());
    }
}
