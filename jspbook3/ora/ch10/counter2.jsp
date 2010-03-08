<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Counter page</title>
  </head>
  <body bgcolor="white">

    <%-- Increment the counter --%>
    <c:set var="sessionCounter" scope="session"
      value="${sessionCounter + 1}" />

    <h1>Counter page</h1>

    This page has been visited <b>${sessionCounter}</b> times
    within the current session.
    <p>
    Click here to load the page through a
    <a href="counter2.jsp">regular link</a>.
    <p>
    Click here to load the page through an
    <a href="<c:url value="counter2.jsp" />">encoded link</a>.
  </body>
</html>
