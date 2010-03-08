<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ora" uri="orataglib" %>

<%-- Verify that the user is logged in --%>
<c:if test="${validUser == null}">
  <jsp:forward page="login.jsp">
    <jsp:param name="origURL" value="${pageContext.request.requestURL}" />
    <jsp:param name="errorMsg" value="Please log in first." />
  </jsp:forward>
</c:if>

<html>
  <head>
    <title>Project Billboard</title>
  </head>
  <body bgcolor="white">

    <form action="storemsg.jsp" method="post">
      <table>
        <tr>
          <td>Project:</td>
          <td>
            <select name="category">
              <option>JSP
              <option>Servlet
              <option>EJB
            </select>
          </td>
        </tr>
        <tr>
          <td colspan=2>
            <textarea name="msg" cols="50" rows="10"></textarea>
          </td>
        </tr>
      </table>
      <input type="submit" value="Post Message">
    </form>
  </body>
</html>
