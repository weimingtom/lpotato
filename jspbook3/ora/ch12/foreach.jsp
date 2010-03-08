<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>All Employees</title>
  </head>
  <body bgcolor="white">

    <%-- Set number of rows to process --%>
    <c:set var="noOfRows" value="2" />

    <c:if test="${empList == null}">
      <sql:query var="empList" scope="session"
        sql="SELECT * FROM Employee ORDER BY LastName" 
      />
    </c:if>

    <c:choose>
      <c:when test="${empList.rowCount == 0}">
        No one seems to work here anymore ...
      </c:when>
      <c:otherwise>
        The following people work here:
        <p>
        <table border="1">
          <th>Last Name</th>
          <th>First Name</th>
          <th>Department</th>
          <th>Email Address</th>
          <c:forEach items="${empList.rows}" var="row" 
            begin="${param.start}" end="${param.start + noOfRows - 1}">
            <tr>
              <td>${fn:escapeXml(row.LastName)}</td>
              <td>${fn:escapeXml(row.FirstName)}</td>
              <td>${fn:escapeXml(row.Dept)}</td>
              <td>${fn:escapeXml(row.EmailAddr)}</td>
            </tr>
          </c:forEach>
        </table>
      </c:otherwise>
    </c:choose>
    <p>
    <c:choose>
      <c:when test="${param.start > 0}">
        <a href="foreach.jsp?start=${param.start - noOfRows}">
          Previous Page</a>
      </c:when>
      <c:otherwise>
          Previous Page
      </c:otherwise>
    </c:choose>
    <c:choose>
      <c:when test="${param.start + noOfRows < empList.rowCount}">
        <a href="foreach.jsp?start=${param.start + noOfRows}">
          Next Page</a>
      </c:when>
      <c:otherwise>
          Next Page
      </c:otherwise>
    </c:choose>
  </body>
</html>
