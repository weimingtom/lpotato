<%@ page language="java" contentType="text/html" %>
<%! 
  int globalCounter = 0; 
%>
<html>
  <head>
    <title>A page with a counter</title>
  </head>
  <body bgcolor="white">
    This page has been visited: <%= ++globalCounter %> times.
    <p>
    <% 
      int localCounter = 0;
    %>
    This counter never increases its value: <%= ++localCounter %>
  </body>
</html>
