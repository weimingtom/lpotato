import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.ora.jsp.util.StringFormat;

/**
 * This servlet is intended only for learning about JSP. It returns
 * the JSP file corresponding to the path passed as pathInfo in a form 
 * that shows the unprocessed JSP and HTML elements. If it wasn't for
 * Internet Explorer, all this servlet would have to do would be to
 * set the Content-Type to "text/plain" and send the file. But Internet
 * Explorer believes it knows better than the application developer,
 * so it bluntantly ignores this header and treats any response that
 * contains HTML elements as "text/html", and therefore renders all
 * HTML elements and ignores the JSP elements. To work around this, the
 * servlet sends the response as "text/html", with all special HTML
 * characters converted to the corresponding HTML character entities.
 * <p>
 * Note! Do not install this servlet on a production server. Doing so
 * may be a security risk, since the source of any file can be delivered
 * to the client, revealing things like database account names and passwords.
 *
 * @version 2.0
 * @author Hans Bergsten, Gefion software
 */
public class JSPSourceServlet extends HttpServlet {

    /**
     * Handles a GET request by sending the contents of the resource
     * specified by the servlet path to the client.
     *
     * @param req an HttpServletRequest
     * @param res an HttpServletResponse
     * @exception IOException
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws IOException {
        // Get the pathInfo
        String path = req.getPathInfo(); 
        
        // Check for invalid characters in the path. For files under
	// WEB-INF, only accept tag files
        if (path.indexOf("..") != -1 || 
	    (path.toUpperCase().indexOf("WEB-INF") != -1) && 
	    !path.endsWith(".tag")){
            res.sendError(HttpServletResponse.SC_FORBIDDEN,
                "Illegal characters found in path " + path);
            return;
        }

        sendFile(path, res);
    }

    /**
     * Copies the "in" reader to the "out" writer.
     *
     * @param in a BufferedReader
     * @param out a PrintWriter
     */
    private void copyFile(BufferedReader in, PrintWriter out)
            throws IOException {
        int chars;
        char[] buf = new char[4096];
        while ((chars = in.read(buf, 0, buf.length)) != -1) {
            out.write(buf, 0, chars);
            out.flush();
        }
    }
    
    /**
     * Sends the contents of a file to the client.
     *
     * @param pathInfo the file URI
     * @param res an HttpServletResponse
     * @exception IOException
     */
    private void sendFile(String path, HttpServletResponse res) 
	throws IOException {
        URL file = getServletContext().getResource(path);
        if (file == null) { 
            res.sendError(HttpServletResponse.SC_NOT_FOUND,
                path + " not found.");
            return;
        }
        
        res.setStatus(HttpServletResponse.SC_OK);
        /* 
	 * Use "text/html" and convert HTML characters to
         * character entities, to fool IE as explained above
	 */
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        // Add HTML header
        out.println("<html><head><title>" + path + "</title></head>");
        out.println("<body><pre>");
        
        // Read the file and convert it
        BufferedReader in = 
	    new BufferedReader(new InputStreamReader(file.openStream()));
        StringWriter rawText = new StringWriter();
        copyFile(in, new PrintWriter(rawText));
        in.close();
        out.write(StringFormat.toHTMLString(rawText.toString()));
        
        // Add HTML footer
        out.println("</pre></body></html>");
        out.close();
    }
}
