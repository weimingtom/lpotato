<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:requestEncoding />

<html>
  <head>
    <title>Processing Non-Western European Input</title>
  </head>
  <body bgcolor="white">
    <h1>Processing Non-Western European Input</h1>

    Text string converted to a Java Unicode string:
    ${fn:escapeXml(param.text)}
    <p>
    Date string converted to the internal Java Date type:
    <fmt:parseDate value="${param.date}" dateStyle="full" />

  </body>
</html>
