<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Request Info</title>
  </head>
  <body bgcolor="white">

    The following information was received:
    <ul>
      <li>Request Method: 
        <c:out value="${pageContext.request.method}" />
      <li>Request Protocol: 
        <c:out value="${pageContext.request.protocol}" />
      <li>Context Path: 
        <c:out value="${pageContext.request.contextPath}" />
      <li>Servlet Path: 
        <c:out value="${pageContext.request.servletPath}" />
      <li>Request URI: 
        <c:out value="${pageContext.request.requestURI}" />
      <li>Request URL: 
        <c:out value="${pageContext.request.requestURL}" />
      <li>Server Name: 
        <c:out value="${pageContext.request.serverName}" />
      <li>Server Port: 
        <c:out value="${pageContext.request.serverPort}" />
      <li>Remote Address: 
        <c:out value="${pageContext.request.remoteAddr}" />
      <li>Remote Host: 
        <c:out value="${pageContext.request.remoteHost}" />
      <li>Secure: 
        <c:out value="${pageContext.request.secure}" />
      <li>Cookies:<br>
        <c:forEach items="${pageContext.request.cookies}" var="c"> 
          &nbsp;&nbsp;<b><c:out value="${c.name}" /></b>:
          <c:out value="${c.value}" /><br>
        </c:forEach>
      <li>Headers:<br>
        <c:forEach items="${headerValues}" var="h"> 
          &nbsp;&nbsp;<b><c:out value="${h.key}" /></b>:
          <c:forEach items="${h.value}" var="value">
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${value}" />
          </c:forEach>
          <br>
        </c:forEach>
    </ul>
  </body>
</html>
