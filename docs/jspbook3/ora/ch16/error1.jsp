<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.Date" %>
<%!
  private String getGreeting() {
    Date now = new Date();
    String greeting = null;
    if (now.getHours() < 12) {
      greeting = "Good morning";
    }
    else if (now.getHours() < 18) {
      greeting = "Good day";
    }
    else {
      greeting = "Good evening";
    }
    return greeting;
  }
%>
<html>
  <head>
    <title>Invalid semicolon use and missing end bracket</title>
  </head>
  <body bgcolor="white">
    <%= getGreeting(); %>
    <% if (request.getParameter("name") == null) { %>
      stranger!
    <% } else { %>
      partner!

    How are you?
  </body>
</html>
