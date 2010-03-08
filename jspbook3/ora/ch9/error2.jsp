<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>JSP is Easy</title>
  </head>
  <body bgcolor="white">

    <h1>JSP is as easy as ...</h1>

    1 + 2 + 3 = <c:out value="${1 + 2 + 3}" >

  </body>
</html>
