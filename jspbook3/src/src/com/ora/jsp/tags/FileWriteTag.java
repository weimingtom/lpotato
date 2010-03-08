package com.ora.jsp.tags;

import java.io.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * This class is a custom action for writing the content of the
 * action element's body to a file specified by an attribute, 
 * or to System.out if no file is specified. If the file name
 * "log" is specified, the standard application log file is
 * used.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class FileWriteTag extends SimpleTagSupport {

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void doTag() throws JspException {
        JspFragment body = getJspBody();
        if (body == null) {
	    throw new JspTagException("'fileWrite' used without a body");
	}
	
        PrintWriter pw = null;
        if (fileName != null && !"log".equals(fileName)) {
            try{
                pw = new PrintWriter(new FileWriter(fileName, true));
            }
            catch (IOException e) {
                throw new JspTagException("Can not open file " + fileName +
                                          " for writing", e);
            }
        }

        ServletContext application = 
            ((PageContext) getJspContext()).getServletContext();
        StringWriter evalResult = new StringWriter();
        try {
            body.invoke(evalResult);
            if (fileName == null) {
                System.out.println(evalResult);
            }
            else if ("log".equals(fileName)) {
                application.log(evalResult.toString());
            }
            else {
                pw.print(evalResult);
            }
        }
        catch (Throwable t) {
            String msg = "Exception in body of " +  this.getClass().getName();
            application.log(msg, t);
            throw new JspTagException(msg, t);
        }
        finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
