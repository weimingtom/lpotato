package com.ora.jsp.beans.news;

import java.io.*;
import java.util.*;

/**
 * This class holds information about one news item. It's
 * only intended as an example.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class NewsItemBean implements Serializable {
    private String category;
    private String msg;
    private String postedBy;
    private Date postedDate = new Date();
    private int id;

    /**
     * Returns the category property value.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category property value.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the msg property value.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the msg property value.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Returns the postedBy property value.
     */
    public String getPostedBy() {
        return postedBy;
    }

    /**
     * Sets the postedBy property value.
     */
    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    /**
     * Returns the postedDate property value.
     */
    public Date getPostedDate() {
        return postedDate;
    }
    
    /**
     * Returns the id property value.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the id property value.
     */
    public void setId(int id) {
        this.id = id;
    }
}
