package com.ora.jsp.beans.shopping;

import java.io.*;

/**
 * This class represents a product. It holds information about the
 * product's name, description and price. All setter methods have
 * package scope, since they are only used by the the CatalogBean.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class ProductBean implements Serializable {
    private String id;
    private String name;
    private String descr;
    private float price;

    /**
     * Returns the product id.
     *
     * @return the product id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the product description.
     *
     * @return the product description
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Returns the product price.
     *
     * @return the product price
     */
    public float getPrice() {
        return price;
    }
    
    /**
     * Sets the product id.
     *
     * @param id the product id
     */
    void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the product name.
     *
     * @param name the product name
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the product description.
     *
     * @param descr the product description
     */
    void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * Sets the product price.
     *
     * @param price the product price
     */
    void setPrice(float price) {
        this.price = price;
    }
}
