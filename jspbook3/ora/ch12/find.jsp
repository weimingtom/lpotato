<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%-- 
  Execute query, with wildcard characters added to the
  parameter values used in the search criteria
--%>
<sql:query var="empList" scope="request">
  SELECT * FROM Employee 
    WHERE FirstName LIKE ?
      AND LastName LIKE ?
      AND Dept LIKE ?
    ORDER BY LastName
  <sql:param value="%${param.firstName}%" />
  <sql:param value="%${param.lastName}%" />
  <sql:param value="%${param.dept}%" />
</sql:query>

<jsp:forward page="list.jsp" />
