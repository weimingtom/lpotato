<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Verify that the user is logged in --%>
<c:if test="${validUser == null}">
  <jsp:forward page="login.jsp">
    <jsp:param name="origURL" value="${pageContext.request.requestURL}" />
    <jsp:param name="errorMsg" value="Please log in first." />
  </jsp:forward>
</c:if>

<%-- Verify that it's a POST method --%>
<c:if test="${pageContext.request.method != 'POST'}">
  <c:redirect url="main.jsp" />
</c:if>

<%-- Create a new news item bean with the submitted info --%>
<jsp:useBean id="newsItem" class="com.ora.jsp.beans.news.NewsItemBean" >
  <jsp:setProperty name="newsItem" property="*" />
  <c:set target="${newsItem}" property="postedBy" 
    value="${validUser.firstName} ${validUser.lastName}" />
</jsp:useBean>

<%-- Add the new news item bean to the list --%>
<c:set target="${news}" property="newsItem" 
  value="${newsItem}" />

<c:redirect url="main.jsp" />
