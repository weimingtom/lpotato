package com.ora.jsp.tags;

import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;

/**
 * This class is a custom action for invalidating a session,
 * causing all session scope variables to be removed and the
 * session to be terminated (marked as invalid).
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class InvalidateSessionTag extends TagSupport {
    /**
     * Invalidates the session.
     */
    public int doEndTag() {
	HttpSession session = pageContext.getSession();
	if (session != null) {
	    session.invalidate();
	}
        return EVAL_PAGE;
    }
}
