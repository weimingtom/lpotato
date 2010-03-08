<%@ taglib prefix="ora" uri="orataglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Temperature Converter</title>
  </head>
  <body bgcolor="white">
    <form action="convert.jsp" method="get">
      <table>
        <tr>
          <td align="right">
            Celsius: 
          </td>
          <td>
            <input name="c" value="${param.c}" size="5">
          </td>
          <td>
            <c:if test="${!empty param.c}">
              Fahrenheit: ${ora:toFahrenheit(param.c)}
            </c:if>
          </td>
        </tr>
        <tr>
          <td align="right">
            Fahrenheit: 
          </td>
          <td>
            <input name="f" value="${param.f}" size="5">
          </td>
          <td>
            <c:if test="${!empty param.f}">
              Celsius: ${ora:toCelsius(param.f)}
            </c:if>
          </td>
        </tr>
        <tr>
          <td>
            <input type="submit" value="Convert">
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
