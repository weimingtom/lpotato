<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>User Info Entry Form</title>
    <script language="JavaScript">
      <!-- Hide from browsers without JavaScript support

      function isValidForm(theForm) {
        if (isEmpty(theForm.userName.value)) {
          theForm.userName.focus();
          return false;
        }
        if (!isValidDate(theForm.birthDate.value)) {
          theForm.birthDate.focus();
          return false;
        }
        if (!isValidEmailAddr(theForm.emailAddr.value)) {
          theForm.emailAddr.focus();
          return false;
        }
        if (!isValidNumber(theForm.luckyNumber.value, 1, 100)) {
          theForm.luckyNumber.focus();
          return false;
        }
        return true;
      }

      function isEmpty(aStr) {
        if (aStr.length == 0) {
          alert("Mandatory field is empty");
          return true;
        }
        return false;
      }

      function isValidDate(dateStr) {
        var matchArray = dateStr.match(/^[0-9]+-[0-1][0-9]-[0-3][0-9]$/)
        if (matchArray == null) {
          alert("Invalid date: " + dateStr);
          return false;
        }
        return true;
      }

      function isValidEmailAddr(emailStr) {
        var matchArray = emailStr.match(/^(.+)@(.+)\.(.+)$/)
        if (matchArray == null) {
          alert("Invalid email address: " + emailStr);
          return false;
        }
        return true;
      }

      function isValidNumber(numbStr, start, stop) {
        var matchArray = numbStr.match(/^[0-9]+$/)
        if (matchArray == null) {
          alert("Invalid number: " + numbStr);
          return false;
        }
        if (numbStr < start || numbStr > stop) {
          alert("Number not within range (" + start + "-" +
            stop + "): " + numbStr);
          return false;
        }
        return true;
      }
      -->
    </script>
  </head>
  <body bgcolor="white">
    <jsp:useBean id="userInfo" 
      scope="request"
      class="com.ora.jsp.beans.userinfo.UserInfoBean"
    />

    <form action="userinfovalidate.jsp" method="post"
      onSubmit="return isValidForm(this)">
      <input type="hidden" name="submitted" value="true">
      <table>
        <c:if test="${param.submitted and userInfo.userNameValid == false}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter your Name
          </font></td></tr>
        </c:if>
        <tr>
          <td>Name:</td>
          <td>
            <input type="text" name="userName"
              value="${fn:escapeXml(userInfo.userName)}">
          </td>
        </tr>
        <c:if test="${param.submitted and not userInfo.birthDateValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter a valid Birth Date
          </font></td></tr>
        </c:if>
        <tr>
          <td>Birth Date:</td>
          <td>
            <input type="text" name="birthDate"
              value="${fn:escapeXml(userInfo.birthDate)}">
          </td>
          <td>(Use format yyyy-mm-dd)</td>
        </tr>
        <c:if test="${param.submitted and not userInfo.emailAddrValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter a valid Email Address
          </font></td></tr>
        </c:if>
        <tr>
          <td>Email Address:</td>
          <td>
            <input type="text" name="emailAddr"
              value="${fn:escapeXml(userInfo.emailAddr)}">
          </td>
          <td>(Use format name@company.com)</td>
        </tr>
        <c:if test="${param.submitted and not userInfo.genderValid}">
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
        <c:if test="${param.submitted and not userInfo.luckyNumberValid}">
          <tr><td></td>
          <td colspan="2"><font color="red">
            Please enter a Lucky Number between 1 and 100
          </font></td></tr>
        </c:if>
        <tr>
          <td>Lucky number:</td>
          <td>
            <input type="text" name="luckyNumber"
              value="${fn:escapeXml(userInfo.luckyNumber)}">
          </td>
          <td>(A number between 1 and 100)</td>
        </tr>
        <c:if test="${param.submitted and not userInfo.foodValid}">
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
              ${userInfo.pastaSelected} ? 'checked' : ''}>Pasta<br>
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
