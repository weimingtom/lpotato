<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%-- Verify that the user is logged in --%>
<c:if test="${validUser == null}">
  <jsp:forward page="login.jsp">
    <jsp:param name="origURL" value="${pageContext.request.requestURL}" />
    <jsp:param name="errorMsg" value="Please log in first." />
  </jsp:forward>
</c:if>

<%-- Verify that it's a POST method --%>
<c:if test="${pageContext.request.method != 'POST'}">
  <c:redirect url="main.jsp" />
</c:if>

<%-- Update the project list in the bean --%>
<c:set target="${validUser}" property="projects"
  value="${paramValues.projects}" />

<sql:transaction>

  <%-- Delete the old project (if any) and insert the new ones --%>
  <sql:update>
    DELETE FROM EmployeeProjects
      WHERE UserName = ?
    <sql:param value="${validUser.userName}" />
  </sql:update>

  <c:forEach items="${validUser.projects}" var="project">
    <sql:update>
      INSERT INTO EmployeeProjects
        (UserName, ProjectName) VALUES(?, ?)
      <sql:param value="${validUser.userName}" />
      <sql:param value="${project}" />
    </sql:update>
  </c:forEach>

</sql:transaction>

<%-- Redirect to main page --%>
<c:redirect url="main.jsp" />
