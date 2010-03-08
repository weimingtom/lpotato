<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>User Info Entry Form</title>
  </head>
  <body bgcolor="white">
    <jsp:useBean id="userInfo" 
      class="com.ora.jsp.beans.userinfo.UserInfoBean">
      <jsp:setProperty name="userInfo" property="*" />
    </jsp:useBean>

    <form action="validate_bean.jsp" method="post">
      <input type="hidden" name="submitted" value="true">
      <table>
        <c:if test="${param.submitted && userInfo.userNameValid == false}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter your Name
          </font></td></tr>
        </c:if>
        <tr>
          <td>Name:</td>
          <td>
            <input type="text" name="userName"
              value="<c:out value="${userInfo.userName}" />">
          </td>
        </tr>
        <c:if test="${param.submitted && !userInfo.birthDateValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter a valid Birth Date
          </font></td></tr>
        </c:if>
        <tr>
          <td>Birth Date:</td>
          <td>
            <input type="text" name="birthDate"
              value="<c:out value="${userInfo.birthDate}" />">
          </td>
          <td>(Use format yyyy-mm-dd)</td>
        </tr>
        <c:if test="${param.submitted && !userInfo.emailAddrValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter a valid Email Address
          </font></td></tr>
        </c:if>
        <tr>
          <td>Email Address:</td>
          <td>
            <input type="text" name="emailAddr"
              value="<c:out value="${userInfo.emailAddr}" />">
          </td>
          <td>(Use format name@company.com)</td>
        </tr>
        <c:if test="${param.submitted && !userInfo.genderValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please select a valid Gender
          </font></td></tr>
        </c:if>
        <tr>
          <td>Gender:</td>
          <td>
            <c:choose>
              <c:when test="${userInfo.gender == 'f'}">
                <input type="radio" name="gender" value="m">Male<br>
                <input type="radio" name="gender" value="f" checked>Female
              </c:when>
              <c:otherwise>
                <input type="radio" name="gender" value="m" checked>Male<br>
                <input type="radio" name="gender" value="f">Female
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
        <c:if test="${param.submitted && !userInfo.luckyNumberValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter a Lucky Number between 1 and 100
          </font></td></tr>
        </c:if>
        <tr>
          <td>Lucky number:</td>
          <td>
            <input type="text" name="luckyNumber"
              value="<c:out value="${userInfo.luckyNumber}" />">
          </td>
          <td>(A number between 1 and 100)</td>
        </tr>
        <c:if test="${param.submitted && !userInfo.foodValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please select only valid Favorite Foods
          </font></td></tr>
        </c:if>
        <tr>
          <td>Favorite Foods:</td>
          <td>
            <input type="checkbox" name="food" value="z"
              ${userInfo.pizzaSelected ? 'checked' : ''}>Pizza<br>
            <input type="checkbox" name="food" value="p"
              ${userInfo.pastaSelected ? 'checked' : ''}>Pasta<br>
            <input type="checkbox" name="food" value="c"
              ${userInfo.chineseSelected ? 'checked' : ''}>Chinese
          </td>
        </tr>
        <tr>
          <td colspan=2>
            <input type="submit" value="Send Data">
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
