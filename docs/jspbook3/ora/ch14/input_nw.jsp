<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${param.language}" />
<c:choose>
  <c:when test="${lang == 'el'}">
    <fmt:setLocale value="el" scope="session" />
  </c:when>
  <c:when test="${lang == 'ru'}">
    <fmt:setLocale value="ru" scope="session" />
  </c:when>
  <c:otherwise>
    <fmt:setLocale value="ja" scope="session" />
    <c:set var="lang" value="ja" />
  </c:otherwise>
</c:choose>

<fmt:setBundle basename="dummy" scope="session" />

<html>
  <head>
    <title>
      Non-Western European Input Test
    </title>
  </head>
  <body bgcolor="white">
    <h1>
      Non-Western European Input Test
    </h1>

    <form action="input_nw.jsp">
      <input type="radio" name="language" value="ja"
        ${lang == 'ja' ? 'checked' : ''}>
        Japanese<br>
      <input type="radio" name="language" value="el"
        ${lang == 'el' ? 'checked' : ''}>
        Greek<br>
      <input type="radio" name="language" value="ru"
        ${lang == 'ru' ? 'checked' : ''}>
        Russian<br>
      <p>
      <input type="submit" 
        value="New Language">
    </form>

    <form action="process_nw.jsp" method="post">
      Enter a date:<br>
      <jsp:useBean id="now" class="java.util.Date" />
      <input type="text" name="date">
      (<fmt:formatDate value="${now}" dateStyle="full"  />)
      <p>
      Enter some text:<br>
      <input type="text" name="text">
      <p>
      <input type="submit" value="Send" >
    </form>
  </body>
</html>
