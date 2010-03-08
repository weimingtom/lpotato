<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="ora" uri="orataglib" %>

<%-- Remove the validUser session bean, if any --%>
<c:remove var="validUser" />
 
<c:if test="${empty param.userName || empty param.password}">
  <c:redirect url="login.jsp" >
    <c:param name="errorMsg" 
      value="You must enter a User Name and Password." />
  </c:redirect>
</c:if>

<%-- 
  See if the user name and password combination is valid. If not,
  redirect back to the login page with a message.
--%>
<sql:query var="empInfo">
  SELECT * FROM Employee 
    WHERE UserName = ? AND Password = ?
  <sql:param value="${param.userName}" />
  <sql:param value="${param.password}" />
</sql:query>

<c:if test="${empInfo.rowCount == 0}">
  <c:redirect url="login.jsp" >
    <c:param name="errorMsg" 
      value="The User Name or Password you entered is not valid." />
  </c:redirect>
</c:if>

<%--    
  Create an EmployeeBean and save it in 
  the session scope and redirect to the appropriate page.
--%>
<c:set var="dbValues" value="${empInfo.rows[0]}" />
<jsp:useBean id="validUser" scope="session"
  class="com.ora.jsp.beans.emp.EmployeeBean" >
  <c:set target="${validUser}" property="userName" 
    value="${dbValues.UserName}" />
  <c:set target="${validUser}" property="firstName" 
    value="${dbValues.FirstName}" />
  <c:set target="${validUser}" property="lastName" 
    value="${dbValues.LastName}" />
  <c:set target="${validUser}" property="dept" 
    value="${dbValues.Dept}" />
  <c:set target="${validUser}" property="empDate" 
    value="${dbValues.EmpDate}" />
  <c:set target="${validUser}" property="emailAddr" 
    value="${dbValues.EmailAddr}" />
</jsp:useBean>

<%-- Add the projects --%>
<sql:query var="empProjects">
  SELECT * FROM EmployeeProjects 
    WHERE UserName = ?
  <sql:param value="${param.userName}" />
</sql:query>

<c:forEach items="${empProjects.rows}" var="project">
  <c:set target="${validUser}" property="project" 
     value="${project.ProjectName}" />
</c:forEach>

<c:choose>
  <c:when test="${!empty param.remember}">
    <ora:addCookie name="userName" 
      value="${param.userName}"
      maxAge="2592000" />
    <ora:addCookie name="password" 
      value="${param.password}"
      maxAge="2592000" />
  </c:when>
  <c:otherwise>
    <ora:addCookie name="userName" 
      value="${param.userName}"
      maxAge="0" />
    <ora:addCookie name="password" 
      value="${param.password}"
      maxAge="0" />
  </c:otherwise>
</c:choose>
   
<%-- 
  Redirect to the main page or to the original URL, if
  invoked as a result of a access attempt to a protected
  page.
--%>
<c:choose>
  <c:when test="${!empty param.origURL}">
    <c:redirect url="${param.origURL}" />
  </c:when>
  <c:otherwise>
    <c:redirect url="main.jsp" />
  </c:otherwise>
</c:choose>
