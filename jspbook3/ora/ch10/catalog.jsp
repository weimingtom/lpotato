<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Product Catalog</title>
  </head>
  <body bgcolor="white">
    <h1>Product Catalog</h1>

    Please select a book from our catalog to read more about it and
    decide if you like to purchase a copy:

    <jsp:useBean id="catalog" scope="application"
      class="com.ora.jsp.beans.shopping.CatalogBean"
    />

    <%-- 
      Generate a list of all products with links to the product page.
    --%>
    <ul>
      <c:forEach items="${catalog.productList}" var="product">
        <c:url var="productURL" value="product.jsp">
          <c:param name="id" value="${product.id}" />
        </c:url>
        <li>
          <a href="${productURL}">${fn:escapeXml(product.name)}</a>
      </c:forEach>
    </ul>

    <jsp:useBean 
      id="cart" scope="session"
      class="com.ora.jsp.beans.shopping.CartBean"
    />

    <%-- Show the contents of the shopping cart, if any --%>
    <c:if test="${!empty cart.productList}">
      Your shopping cart contains the following items:
      <p>
      <table border=0>
        <c:forEach items="${cart.productList}" var="product">
          <tr>
            <td>${fn:escapeXml(product.name)}</td>
            <td>
              <fmt:formatNumber value="${product.price}" 
                type="currency" />
            </td>
          </tr>
        </c:forEach>

        <tr><td colspan=2><hr></td></tr>
        <tr>
          <td><b>Total:</b></td>
          <td>
            <fmt:formatNumber value="${cart.total}" 
              type="currency" />
          </td>
        </tr>
      </table>
    </c:if>
  </body>
</html>
