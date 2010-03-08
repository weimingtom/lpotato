<%@ taglib prefix="ora" uri="orataglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
  <head>
    <title>Calendar</title>
  </head>
  <body bgcolor="white">
    <jsp:useBean id="now" class="java.util.Date" />

    <table border="1" cellspacing="0">
      <caption>
        <fmt:formatDate value="${now}" pattern="MMMM yyyy" />
      </caption>
      <ora:calendar date="${now}" var="c">
        <jsp:attribute name="beforePattern">
          <tr>
        </jsp:attribute>
        <jsp:attribute name="afterPattern">
          </tr>
        </jsp:attribute>
        <jsp:attribute name="dayNamePattern">
          <th><fmt:formatDate value="${c}" pattern="EE" /></th>
        </jsp:attribute>
        <jsp:attribute name="padPattern">
          <td bgcolor="lightgrey" width="30" height="30" valign="top">
            <fmt:formatDate value="${c}" pattern="d" />
          </td>
        </jsp:attribute>
        <jsp:attribute name="weekdayPattern">
          <td bgcolor="lightblue" width="30" height="30" valign="top">
            <fmt:formatDate value="${c}" pattern="d" />
          </td>
        </jsp:attribute>
        <jsp:attribute name="weekendPattern">
          <td bgcolor="yellow" width="30" height="30" valign="top">
            <fmt:formatDate value="${c}" pattern="d" />
          </td>
        </jsp:attribute>
      </ora:calendar>
    </table>
    <br>
    <br>
    <code>
      <fmt:formatDate value="${now}" pattern="MMMM yyyy" />
      <br>
      <ora:calendar date="${now}" var="c">
        <jsp:attribute name="afterPattern">
          <br>
        </jsp:attribute>
        <jsp:attribute name="padPattern">
          | ------ |
        </jsp:attribute>
        <jsp:attribute name="weekdayPattern">
          | <fmt:formatDate value="${c}" pattern="EE dd" /> |
        </jsp:attribute>
      </ora:calendar>
    </code>
  </body>
</html>
