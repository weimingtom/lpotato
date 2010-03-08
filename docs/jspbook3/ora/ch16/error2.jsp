<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.Date" %>
<html>
  <head>
    <title>Scriptlet instead of expression</title>
  </head>
  <body bgcolor="white">
    Howdy
    <% if (request.getParameter("name") == null) { %>
      stranger!
    <% } else { %>
      partner!
    <% } %>
    It's <% new Date().toString() %> and all is well.
  </body>
</html>
