package com.ora.jsp.tlv;

import java.util.*;
import javax.servlet.jsp.tagext.*;
import org.jdom.*;
import org.jdom.input.*;

/**
 * This is an example of a TagLibraryValidator class. It only validates
 * the use of the dummy &lt;xmp:child&gt; action element in a dummy tag
 * library, but it serves as an extendable validator for a real tag
 * library.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class OraTLV extends TagLibraryValidator {
    private SAXBuilder builder = new SAXBuilder();
    private Namespace jspNamespace = 
	Namespace.getNamespace("http://java.sun.com/JSP/Page");

    /**
     * Returns a ValidationMessage array with information about validation
     * errors, or null if no errors are found.
     */
    public ValidationMessage[] validate(String prefix, String uri, 
        PageData pd) {
	
	ValidationMessage[] vms = null;
	ArrayList msgs = new ArrayList();
	Namespace taglibNamespace = Namespace.getNamespace(uri);
        try {
            Document doc = builder.build(pd.getInputStream());
            Element root = doc.getRootElement();
	    validateElement(root, taglibNamespace, msgs);
	}
        catch (Exception e) {
	    vms = new ValidationMessage[1];
	    vms[0] = new ValidationMessage(null, e.getMessage()); 
        } 

	if (msgs.size() != 0) {
	    vms = new ValidationMessage[msgs.size()];
	    msgs.toArray(vms);
	}
	return vms;
    }

    /**
     * Dispatches to the appropriate validation method for the
     * specified element, and calls itself recursively for all
     * children of the element.
     */
    private void validateElement(Element e, Namespace ns, ArrayList msgs) {
	if (ns.equals(e.getNamespace())) {
	    if (e.getName().equals("child")) {
		validateChild(e, ns, msgs);
	    }
	}
	if (e.hasChildren()) {
	    List kids = e.getChildren();
	    Iterator i = kids.iterator();
	    while(i.hasNext()) {
		validateElement((Element) i.next(), ns, msgs);
	    }
	}
    }

    /**
     * Validates that a "child" element is only used within the body of
     * a "parent" element. If not, a ValidationMessage is added to the
     * message array.
     */
    private void validateChild(Element e, Namespace ns, ArrayList msgs) {
	Element parent = findParent(e, ns, "parent");
	if (parent == null) {
	    String id = e.getAttributeValue("id", jspNamespace);
	    ValidationMessage vm = new ValidationMessage(id,
		e.getQualifiedName() + " must only be used with 'parent'");
	    msgs.add(vm);
	}
    }

    /**
     * Returns the closest parent element of the specified element that
     * matches the specified namespace and name.
     */
    private Element findParent(Element e, Namespace ns, String name) {
	if (e.getName().equals(name) && 
	    ns.equals(e.getNamespace())) {
	    return e;
	}
	Element parent = e.getParent();
	if (parent != null) {
	    return findParent(parent, ns, name);
	}
	return null;
    }
}
