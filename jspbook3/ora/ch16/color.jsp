<%@ page language="java" contentType="text/html" %>
<%!
  String randomColor() {
    java.util.Random random = new java.util.Random();
    int red = (int) (random.nextFloat() * 255);
    int green = (int) (random.nextFloat() * 255);
    int blue = (int) (random.nextFloat() * 255);
    return "#" + 
      Integer.toString(red, 16) + 
      Integer.toString(green, 16) + 
      Integer.toString(blue, 16);
    }
%>
<html>
  <head>
    <title>Random Color</title>
  </head>
  <body bgcolor="white">

    <h1>Random Color</h1>

    <table bgcolor="<%= randomColor() %>" >
      <tr><td width="100" height="100">&nbsp;</td></tr>
    </table>

  </body>
</html>
