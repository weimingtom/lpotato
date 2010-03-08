<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="errorpage.jsp?debug=log" %>

<html>
  <head>
    <title>Calculator</title>
  </head>
  <body bgcolor="white">

    <jsp:useBean id="calc" class="com.ora.jsp.beans.calc.CalcBean">
      <jsp:setProperty name="calc" property="*" />
    </jsp:useBean>

    <%-- Calculate the new numbers and state info --%>
    <c:set var="currentNumber" value="${calc.currentNumber}" />

    <form action="calc.jsp" method="post">
      <table border=1>
        <tr>
          <td colspan="4" align="right">
            <c:choose>
              <c:when test="${currentNumber == ''}">
                &nbsp;
              </c:when>
              <c:otherwise>
                <c:out value="${currentNumber}" />
              </c:otherwise>
            </c:choose>
            <input type="hidden" name="currentNumber" value="${currentNumber}">
            <input type="hidden" name="previousNumber"
              value="${calc.previousNumber}">
            <input type="hidden" name="currentOperation"
              value="${calc.currentOperation}">
            <input type="hidden" name="reset" value="${calc.reset}">
          </td>
        </tr>
        <tr>
          <td><input type="submit" name="digit" value=" 7 "></td>
          <td><input type="submit" name="digit" value=" 8 "></td>
          <td><input type="submit" name="digit" value=" 9 "></td>
          <td><input type="submit" name="oper" value="  /  "></td>
        </tr>
        <tr>
          <td><input type="submit" name="digit" value=" 4 "></td>
          <td><input type="submit" name="digit" value=" 5 "></td>
          <td><input type="submit" name="digit" value=" 6 "></td>
          <td><input type="submit" name="oper" value="  *  "></td>
        </tr>
        <tr>
          <td><input type="submit" name="digit" value=" 1 "></td>
          <td><input type="submit" name="digit" value=" 2 "></td>
          <td><input type="submit" name="digit" value=" 3 "></td>
          <td><input type="submit" name="oper" value="  -  "></td>
        </tr>
        <tr>
          <td><input type="submit" name="digit" value=" 0 "></td>
          <td>&nbsp;</td>
          <td><input type="submit" name="dot" value="  .  "></td>
          <td><input type="submit" name="oper" value="  +  "></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td><input type="submit" name="clear" value=" C "></td>
          <td><input type="submit" name="oper" value="  =  "></td>
      </table>
    </form>

  </body>
</html>
