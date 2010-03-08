<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Search Result</title>
  </head>
  <body bgcolor="white">

  <c:choose>
    <c:when test="${empList.rowCount == 0}">
      Sorry, no employees were found.
    </c:when>
    <c:otherwise>
      The following employees were found:
      <p>
      <table border="1">
        <th>Last Name</th>
        <th>First Name</th>
        <th>Department</th>
        <th>Email Address</th>
        <th>Modified</th>
        <c:forEach items="${empList.rows}" var="row">
          <tr>
            <td>${fn:escapeXml(row.LastName)}</td>
            <td>${fn:escapeXml(row.FirstName)}</td>
            <td>${fn:escapeXml(row.Dept)}</td>
            <td>${fn:escapeXml(row.EmailAddr)}</td>
            <td>${fn:escapeXml(row.ModDate)}</td>
            <td>
              <form action="delete.jsp" method="post">
                <input type="hidden" name="userName"
                  value="${fn:escapeXml(row.UserName)}">
                <input type="hidden" name="firstName"
                  value="${fn:escapeXml(param.firstName)}">
                <input type="hidden" name="lastName"
                  value="${fn:escapeXml(param.lastName)}">
                <input type="hidden" name="dept"
                  value="${fn:escapeXml(param.dept)}">
                <input type="submit" value="Delete">
              </form>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>
  </body>
</html>
