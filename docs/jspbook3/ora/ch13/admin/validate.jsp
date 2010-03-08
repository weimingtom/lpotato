<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ora" uri="orataglib" %>

<c:set var="isValid" value="true" />

<c:if test="${empty param.userName}">
  <c:set var="userNameError" scope="request"
    value="User Name missing" />
  <c:set var="isValid" value="false" />
</c:if>
<c:if test="${empty param.password}">
  <c:set var="passwordError" scope="request"
    value="Password missing" />
  <c:set var="isValid" value="false" />
</c:if>
<c:if test="${empty param.firstName}">
  <c:set var="firstNameError" scope="request"
    value="First Name missing" />
  <c:set var="isValid" value="false" />
</c:if>
<c:if test="${empty param.lastName}">
  <c:set var="lastNameError" scope="request"
    value="Last Name missing" />
  <c:set var="isValid" value="false" />
</c:if>
<c:if test="${empty param.dept}">
  <c:set var="deptError" scope="request"
    value="Department missing" />
  <c:set var="isValid" value="false" />
</c:if>

<%-- Validate date by catching a possible exception --%>
<c:catch var="invalidDate">
  <fmt:parseDate value="${param.empDate}" pattern="yyyy-MM-dd"
    var="ignore" />
</c:catch>
<c:choose>
  <c:when test="${empty param.empDate}">
    <c:set var="empDateError" scope="request"
      value="Employment Date missing" />
    <c:set var="isValid" value="false" />
  </c:when>
  <c:when test="${invalidDate != null}">
    <c:set var="empDateError" scope="request"
      value="Invalid Employment Date" />
    <c:set var="isValid" value="false" />
  </c:when>
</c:choose>

<%-- Validate email address format using custom action --%>
<ora:ifValidEmailAddr value="${param.emailAddr}"
  var="isValidEmailAddr" />
<c:choose>
  <c:when test="${empty param.emailAddr}">
    <c:set var="emailAddrError" scope="request"
      value="Email Address missing" />
    <c:set var="isValid" value="false" />
  </c:when>
  <c:when test="${!isValidEmailAddr}">
    <c:set var="emailAddrError" scope="request"
      value="Invalid Email Address" />
    <c:set var="isValid" value="false" />
  </c:when>
</c:choose>

<c:choose>
  <c:when test="${isValid}">
    <jsp:forward page="store.jsp" />
  </c:when>
  <c:otherwise>
    <jsp:forward page="enter.jsp" />
  </c:otherwise>
</c:choose>
