<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>User Info Entry Form</title>
  </head>
  <body bgcolor="white">
    <form action="input_bean.jsp" method="post">
      <table>
        <tr>
          <td>Name:</td>
          <td>
            <input type="text" name="userName">
          </td>
        </tr>
        <tr>
          <td>Birth Date:</td>
          <td>
            <input type="text" name="birthDate">
          </td>
          <td>(Use format yyyy-mm-dd)</td>
        </tr>
        <tr>
          <td>Email Address:</td>
          <td>
            <input type="text" name="emailAddr">
          </td>
          <td>(Use format name@company.com)</td>
        </tr>
        <tr>
          <td>Gender:</td>
          <td>
            <input type="radio" name="gender" value="m" checked>Male<br>
            <input type="radio" name="gender" value="f">Female
          </td>
        </tr>
        <tr>
          <td>Lucky number:</td>
          <td>
            <input type="text" name="luckyNumber">
          </td>
          <td>(A number between 1 and 100)</td>
        </tr>
        <tr>
          <td>Favorite Foods:</td>
          <td>
            <input type="checkbox" name="food" value="z">Pizza<br>
            <input type="checkbox" name="food" value="p">Pasta<br>
            <input type="checkbox" name="food" value="c">Chinese
          </td>
        </tr>
        <tr>
          <td colspan=2>
            <input type="submit" value="Send Data">
          </td>
        </tr>
      </table>
    </form>

    <jsp:useBean id="userInfo" 
      class="com.ora.jsp.beans.userinfo.UserInfoBean">
      <jsp:setProperty name="userInfo" property="*" />
    </jsp:useBean>

    You entered:<br>
    Name: <c:out value="${userInfo.userName}" /><br>
    Birth Date: <c:out value="${userInfo.birthDate}" /><br>
    Email Address: <c:out value="${userInfo.emailAddr}" /><br>
    Gender: <c:out value="${userInfo.gender}" /><br>
    Lucky Number: <c:out value="${userInfo.luckyNumber}" /><br>
    Favorite Food:
      <c:forEach items="${userInfo.food}" var="current">
        <c:out value="${current}" />&nbsp;
      </c:forEach>
  </body>
</html>
