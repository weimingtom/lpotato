<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Employee Info Stored</title>
  </head>
  <body bgcolor="white">
    This is the information stored in the employee database:

    <table>
      <c:forEach items="${newEmpDbInfo.rows}" var="row">
        <c:forEach items="${row}" var="column">
          <tr>
            <td align=right>
              <b>${fn:escapeXml(column.key)}:</b>
            </td>
            <td>
              ${fn:escapeXml(column.value)}
            </td>
          </tr>
        </c:forEach>
      </c:forEach>
    </table>

  </body>
</html>
