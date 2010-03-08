<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Looking for information</title>
  </head>
  <body bgcolor="white">

    <h1>Looking for information</h1>

    The missing parameter: <c:out value="${param.misspelled}" />

  </body>
</html>
