package com.ora.jsp.tags.xmp;

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
 * @version 1.0
 */
public class ClassicFileWriteTag extends BodyTagSupport 
    implements TryCatchFinally {

    private String fileName;
    private PrintWriter pw;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Makes sure the specified file can be opened for writing.
     * If so, tells the container to evaluate the body.
     */
    public int doStartTag() throws JspException {
        if (fileName != null && !"log".equals(fileName)) {
            try{
                pw = new PrintWriter(new FileWriter(fileName, true));
            }
            catch (IOException e) {
                throw new JspException("Can not open file " + fileName +
                                       " for writing", e);
            }
        }
        return EVAL_BODY_BUFFERED;
    }

    /**
     * Writes the content accumulated in bodyContent to the
     * file.
     */
    public int doAfterBody() throws JspException {
        String content = bodyContent.getString();
        if (fileName == null) {
            System.out.println(content);
        }
        else if ("log".equals(fileName)) {
            ServletContext application = pageContext.getServletContext();
            application.log(content);
        }
        else {
            pw.print(bodyContent.getString());
        }
        return SKIP_BODY;
    }

    /**
     * Log the problem and rethrow the exception.
     */
    public void doCatch(Throwable t) throws Throwable {
        ServletContext application = pageContext.getServletContext();
        application.log("Exception in body of " + 
            this.getClass().getName(), t);
        throw t;
    }

    /**
     * Closes the file, no matter if an exception was thrown
     * by the body evaluation or not. Note that this method is
     * called even if an exception is thrown by doStartTag(), or
     * any other doXXX() method. In this case, the file may not
     * have been opened at all.
     */
    public void doFinally() {
        if (pw != null) {
            pw.close();
        }
    }
}
