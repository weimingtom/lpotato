<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="uri" value="${pageContext.request.servletPath}" />
<table bgcolor="lightblue">
  <tr>
    <td>
      <c:choose>
        <c:when test="${uri == '/ch17/page1.jsp'}">
          <b>Page 1</b>
        </c:when>
        <c:otherwise>
          <a href="page1.jsp">Page 1</a>
        </c:otherwise>
      </c:choose>
    </td>
  </tr>
  <tr>
    <td>
      <c:choose>
        <c:when test="${uri == '/ch17/page2.jsp'}">
          <b>Page 2</b>
        </c:when>
        <c:otherwise>
          <a href="page2.jsp">Page 2</a>
        </c:otherwise>
      </c:choose>
    </td>
  </tr>
  <tr>
    <td>
      <c:choose>
        <c:when test="${uri == '/ch17/page3.jsp'}">
          <b>Page 3</b>
        </c:when>
        <c:otherwise>
          <a href="page3.jsp">Page 3</a>
        </c:otherwise>
      </c:choose>
    </td>
  </tr>
</table>
