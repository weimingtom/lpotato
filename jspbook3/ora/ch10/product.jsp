<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Product Description</title>
  </head>
  <body bgcolor="white">

    <jsp:useBean id="catalog" scope="application"
      class="com.ora.jsp.beans.shopping.CatalogBean"
    />

    <%-- Get the specified ProductBean from the catalog --%>
    <c:set var="product" value="${catalog.productsById[param.id]}" />

    <h1>${fn:escapeXml(product.name)}</h1>

    ${fn:escapeXml(product.descr)}

    <p>
    <c:url var="addtocartURL" value="addtocart.jsp">
      <c:param name="id" value="${product.id}" />
    </c:url>
 
    <a href="${addtocartURL}">
      Add this book to the shopping cart</a>

  </body>
</html>
