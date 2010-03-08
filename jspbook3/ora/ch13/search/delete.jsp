<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sql:update>
  DELETE FROM Employee 
    WHERE UserName = ?
  <sql:param value="${param.userName}" />
</sql:update>

<c:redirect url="find.jsp">
  <c:param name="firstName" 
    value="${param.firstName}" />
  <c:param name="lastName" 
    value="${param.lastName}" />
  <c:param name="dept" 
    value="${param.dept}" />
</c:redirect>
