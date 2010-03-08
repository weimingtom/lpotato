package com.ora.jsp.beans.motd;

import java.util.*;
/**
 * This is an example of a simple bean with one property that holds
 * names of image files. Every time the property is read, a new
 * file name is returned.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class CartoonBean implements java.io.Serializable {
    private static int index = -1;
    private List fileNames;

    /**
     * Constructor that creates an internal data structure to hold
     * the image file names.
     */
    public CartoonBean() {
        initFileList();
    }

    /**
     * Returns a new file name every time it's called, cycling through
     * all available image files.
     */
    public String getFileName() {
        index++;
        if (index > fileNames.size() - 1) {
            index = 0;
        }
        return (String) fileNames.get(index);
    }

    /**
     * Creates and initializes a data structure to hold the image file
     * names.
     */
    private void initFileList() {
        fileNames = new ArrayList();
        fileNames.add("dilbert2001113293109.gif");
        fileNames.add("dilbert2001166171101.gif");
        fileNames.add("dilbert2001166171108.gif");
        fileNames.add("dilbert2731150011029.gif");
    }
}
