package com.ora.jsp.tags.xmp;

import java.util.*;
import java.lang.reflect.Array;
import javax.servlet.jsp.*;
import javax.servlet.jsp.jstl.core.*;
import org.apache.taglibs.standard.lang.support.*;
import com.ora.jsp.util.StringFormat;

/**
 * This class is a custom action for creating HTML select lists,
 * checkboxes or radio buttons, where some items need to be
 * marked as selected based on dynamic data.
 * The names and values for the options are provided through a Map, 
 * and the selected values are provided as a String[]. The action
 * exposes a bean with three properties to actions in the body,
 * representing the current Map entry:
 * <ul>
 *  <li>String text
 *  <li>String value
 *  <li>boolean selected
 * </ul>
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class ForEachOptionTag extends LoopTagSupport {
    private Map options;
    private String[] selections;
    private Iterator iterator;

    public void setOptions(Map options) {
        this.options = options;
    }

    public void setSelections(String[] selections) {
        this.selections = selections;
    }

    protected void prepare() {
        if (options != null) {
            iterator = options.entrySet().iterator();
        }
    }

    protected Object next() {
        Map.Entry me = (Map.Entry) iterator.next();
        String text = (String) me.getKey();
        String value = (String) me.getValue();
        boolean selected = isSelected(value);
        return new OptionBean(text, value, selected);
    }

    protected boolean hasNext() {
        if (iterator == null) {
            return false;
        }
        else {
            return iterator.hasNext();
        }
    }

    private boolean isSelected(String value) {
        return StringFormat.isValidString(value, selections, false); 
    }

    public class OptionBean {
        private String text;
        private String value;
        private boolean selected;

        public OptionBean(String text, String value, boolean selected) {
            this.text = text;
            this.value = value;
            this.selected = selected;
        }

        public String getText() {
            return text;
        }

        public String getValue() {
            return value;
        }

        public boolean isSelected() {
            return selected;
        }
    }
}
