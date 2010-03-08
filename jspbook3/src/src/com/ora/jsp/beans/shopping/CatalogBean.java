package com.ora.jsp.beans.shopping;

import java.io.*;
import java.util.*;

/**
 * This class represents a product catalog. It holds a list of
 * products available for sale.
 * <p>
 * This is just a demo so the product list is hardcoded, created
 * at instantiation. A real version would get the information from
 * an external data source.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class CatalogBean implements Serializable {
    private Map catalog = new HashMap();

    /**
     * Constructor. Creates all ProductBean objects and adds them
     * to the catalog.
     */
    public CatalogBean() {
        ProductBean prod = new ProductBean();
        prod.setId("1000");
        prod.setName("JavaServer Pages");
        prod.setDescr("Learn how to develop a JSP based web application.");
        prod.setPrice(32.95f);
        catalog.put("1000", prod);

        prod = new ProductBean();
        prod.setId("2000");
        prod.setName("Java Servlet Programming");
        prod.setDescr("Learn how to develop a servlet based web application.");
        prod.setPrice(32.95f);
        catalog.put("2000", prod);

        prod = new ProductBean();
        prod.setId("3000");
        prod.setName("Java In a Nutshell");
        prod.setDescr("Learn the Java programming language.");
        prod.setPrice(32.95f);
        catalog.put("3000", prod);
    }

    /**
     * Returns all products as an Iterator, suitable for looping.
     *
     * @return an Iterator for all ProductBean instances
     */
    public ProductBean[] getProductList() {
	ProductBean[] products = new ProductBean[catalog.size()];
	catalog.values().toArray(products);
        return products;
    }

    /**
     * Returns a Map with all ProductBean instances, keyed by ID.
     *
     * @return an Map of all ProductBean instances
     */
    public Map getProductsById() {
        return catalog;
    }
}
