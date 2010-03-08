<%@ page language="java" contentType="text/html" %>
<html>
  <head>
    <title>Browser Check</title>
  </head>
  <body bgcolor="white">

    <%
       String userAgent = request.getHeader("User-Agent");
       if (userAgent.indexOf("MSIE") != -1) { 
    %>
         You're using Internet Explorer.
    <% } else if (userAgent.indexOf("Mozilla") != 1) { %>
         You're probably using Netscape.
    <% } else { %>
         You're using a browser I don't know about.
    <% } %>

  </body>
</html>
