<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 
  Set the locale to the selected one, if any. Otherwise, let the
  <fmt:setBundle> action pick the best one based on the Accept-Language
  header.
--%>
<c:if test="${param.language == 'en'}">
  <fmt:setLocale value="en" scope="session" />
</c:if>
<c:if test="${param.language == 'sv'}">
  <fmt:setLocale value="sv" scope="session" />
</c:if>
<c:if test="${param.language == 'de'}">
  <fmt:setLocale value="de" scope="session" />
</c:if>

<fmt:setBundle basename="pages" var="pagesBundle" />
<fmt:setBundle basename="labels" scope="session" />

<html>
  <head>
    <title>
      <fmt:message key="title" />
    </title>
  </head>
  <body bgcolor="white">
    <h1>
      <fmt:message key="title" />
    </h1>

    <fmt:message key="select_language" />:
    <form action="poll.jsp">
      <p>
      <c:set var="currLang" value="${pagesBundle.locale.language}" />
      <input type="radio" name="language" value="en"
        ${currLang == 'en' ? 'checked' : ''}>
      <fmt:message key="english" /><br>
      <input type="radio" name="language" value="sv"
        ${currLang == 'sv' ? 'checked' : ''}>
      <fmt:message key="swedish" /><br>
      <input type="radio" name="language" value="de"
        ${currLang == 'de' ? 'checked' : ''}>
      <fmt:message key="german" /><br>
      <p>
      <input type="submit" 
        value="<fmt:message key="new_language" />">
    </form>

    <a href="<fmt:message key="details_page" bundle="${pagesBundle}" />">
      <fmt:message key="question" />
    </a>

    <form action="calculate.jsp" method="post">
      <input type="radio" name="answerId" value="1" checked>
      <fmt:message key="answer1" />
      <br>
      <input type="radio" name="answerId" value="2">
      <fmt:message key="answer2" />
      <br>
      <input type="radio" name="answerId" value="3">
      <fmt:message key="answer3" />
      <p>
      <input type="submit" 
        value="<fmt:message key="submit" />">
    </form>
  </body>
</html>
