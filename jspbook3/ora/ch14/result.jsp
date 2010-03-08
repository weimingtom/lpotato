<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
  <head>
    <title>
      <fmt:message key="title" />
    </title>
  </head>
  <body bgcolor="white">
    <jsp:useBean id="pollResult" scope="application"
       class="com.ora.jsp.beans.poll.PollBean" />

    <jsp:useBean id="now" class="java.util.Date" />

    <h1>
      <fmt:message key="result" />:
      <fmt:formatDate value="${now}" />
    </h1>

    <fmt:message key="question" />
    <p>
    <fmt:message key="number_of_votes" />:
    <fmt:formatNumber value="${pollResult.total}" />

    <table width="70%">
      <tr>
        <td width="30%">
          <fmt:message key="result1">
            <fmt:param value="${pollResult.answer1Percent}" />
            <fmt:param value="${pollResult.answer1}" />
          </fmt:message>
        </td>
        <td>
          <table 
            width="<fmt:formatNumber 
              value="${pollResult.answer1Percent}"/>%"
            bgcolor="lightgreen">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td width="30%">
          <fmt:message key="result2">
            <fmt:param value="${pollResult.answer2Percent}" />
            <fmt:param value="${pollResult.answer2}" />
          </fmt:message>
        </td>
        <td>
          <table 
            width="<fmt:formatNumber 
              value="${pollResult.answer2Percent}"/>%"
            bgcolor="lightblue">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td width="30%">
          <fmt:message key="result3">
            <fmt:param value="${pollResult.answer3Percent}" />
            <fmt:param value="${pollResult.answer3}" />
          </fmt:message>
        </td>
        <td>
          <table 
            width="<fmt:formatNumber 
              value="${pollResult.answer3Percent}"/>%"
            bgcolor="orange">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
