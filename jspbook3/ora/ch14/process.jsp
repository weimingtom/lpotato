<%@ page contentType="text/html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
  <head>
    <title>Parsed Date and Number</title>
  </head>
  <body bgcolor="white">
    <h1>Parsed Date and Number</h1>

    Date string converted to the internal Java Date type:
    <fmt:parseDate value="${param.date}" dateStyle="full" />
    <p>
    Number string converted to the internal Java Number type:
    <fmt:parseNumber value="${param.number}" pattern="####.00" />
  </body>
</html>
