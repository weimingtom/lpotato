<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Project Billboard</title>
  </head>
  <body bgcolor="white">

    <h1>Welcome ${fn:escapeXml(validUser.firstName)}</h1>
    <h2>Number of active sessions: ${session_counter[0]}</h2>
    Your profile currently shows you like information about the 
    following checked-off projects. If you like to update your
    profile, make the appropriate changes below and click 
    Update Profile.
    <form action="updateProfile.do" method="post">

      <c:forEach items="${validUser.projects}" var="current">
        <c:choose>
          <c:when test="${current == 'JSP'}"> 
            <c:set var="jspSelected" value="true" />
          </c:when>
          <c:when test="${current == 'Servlet'}"> 
            <c:set var="servletSelected" value="true" />
          </c:when>
          <c:when test="${current == 'EJB'}"> 
            <c:set var="ejbSelected" value="true" />
          </c:when>
        </c:choose>
      </c:forEach>
      <input type="checkbox" name="projects" value="JSP"
        ${jspSelected ? 'checked' : ''}>JSP<br>
      <input type="checkbox" name="projects" value="Servlet"
        ${servletSelected ? 'checked' : ''}>Servlet<br>
      <input type="checkbox" name="projects" value="EJB"
        ${ejbSelected ? 'checked' : ''}>EJB<br>
      <input type="submit" value="Update Profile">
    </form>
    <hr>

    When you're done reading the news, please <a href="../logout.do">
    log out</a>.

    <hr>
    <a href="entermsg.jsp">Post a new message</a>
    <p>

    <%-- Get all new items --%>
    <jsp:useBean id="news" scope="application"
      class="com.ora.jsp.beans.news.NewsBean" />
    <c:set var="newsItems" value="${news.newsItems}" />

    <%--
       Loop through all user projects and for each, loop through 
       all news items and display the ones that match the current 
       project.
    --%>
    <table>
      <c:forEach items="${validUser.projects}" var="projectName">
        <tr>
          <td colspan="2">
            <b>Project: <c:out value="${projectName}" /></b>
          </td>
        </tr>
        <c:forEach items="${newsItems}" var="newsItem">
          <c:if test="${newsItem.category == projectName}">
            <tr>
              <td>
                ${fn:escapeXml(newsItem.postedBy)}
              </td>
              <td>
                ${fn:escapeXml(newsItem.postedDate)}
              </td>
            </tr>
            <tr>
              <td colspan="2">
                ${fn:escapeXml(newsItem.msg)}
              </td>
            </tr>
            <tr>
              <td colspan="2"><hr></td>
            </tr>
          </c:if>
        </c:forEach>
      </c:forEach>
    </table>
  </body>
</html>
