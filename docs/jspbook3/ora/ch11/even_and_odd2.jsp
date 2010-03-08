<%@ page contentType="text/html" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/mytags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Even and Odd Rows</title>
  </head>
  <body bgcolor="white">
    <h1>Even and Odd Rows</h1>
    <table>
      <my:forEvenAndOdd2 items="a,b,c,d,e">
        <jsp:attribute name="even">
          <c:set var="counter" value="${counter + 1}" />
          <tr bgcolor="red"><td>${counter}: Even Row: ${current}</td></tr>
        </jsp:attribute>
        <jsp:attribute name="odd">
          <c:set var="counter" value="${counter + 1}" />
          <tr bgcolor="blue"><td>${counter}: Odd Row: ${current}</td></tr>
        </jsp:attribute>
      </my:forEvenAndOdd2>
    </table>
  </body>
</html>
