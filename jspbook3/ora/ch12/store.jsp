<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 
  See if the employee is already defined. If not, insert the
  info, else update it.
--%>
<sql:query var="empDbInfo">
  SELECT * FROM Employee 
    WHERE UserName = ?
  <sql:param value="${param.userName}" />
</sql:query>

<%--
  Deal with the date values: parse the employment date and create a
  Date object from it, and create a new variable to hold the current
  date.
--%>
<fmt:parseDate value="${param.empDate}" var="parsedEmpDate" 
  pattern="yyyy-MM-dd" />
<jsp:useBean id="now" class="java.util.Date" />

<c:choose>
  <c:when test="${empDbInfo.rowCount == 0}">
    <sql:update>
      INSERT INTO Employee 
        (UserName, Password, FirstName, LastName, Dept, 
          EmpDate, EmailAddr, ModDate)
        VALUES(?, ?, ?, ?, ?, ?, ?, ?)
      <sql:param value="${param.userName}" />
      <sql:param value="${param.password}" />
      <sql:param value="${param.firstName}" />
      <sql:param value="${param.lastName}" />
      <sql:param value="${param.dept}" />
      <sql:dateParam value="${parsedEmpDate}" type="date" />
      <sql:param value="${param.emailAddr}" />
      <sql:dateParam value="${now}" />
    </sql:update>
  </c:when>
  <c:otherwise>
    <sql:update>
      UPDATE Employee
        SET Password = ?, 
            FirstName = ?, 
            LastName = ?, 
            Dept = ?,
            EmpDate = ?,
            EmailAddr = ?,
            ModDate = ?
        WHERE UserName = ?
      <sql:param value="${param.password}" />
      <sql:param value="${param.firstName}" />
      <sql:param value="${param.lastName}" />
      <sql:param value="${param.dept}" />
      <sql:dateParam value="${parsedEmpDate}" type="date" />
      <sql:param value="${param.emailAddr}" />
      <sql:dateParam value="${now}" />
      <sql:param value="${param.userName}" />
    </sql:update>
  </c:otherwise>
</c:choose>

<%-- Get the new or updated data from the database --%>
<sql:query var="newEmpDbInfo" scope="session">
  SELECT * FROM Employee 
    WHERE UserName = ?
  <sql:param value="${param.userName}" />
</sql:query>

<%-- Redirect to the confirmation page --%>
<c:redirect url="confirmation.jsp" />
