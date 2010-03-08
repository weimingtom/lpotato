<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Employee Entry Form</title>
  </head>
  <body bgcolor="white">

    Please enter information about an employee below:
    <form action="validate.jsp" method="post">
      <table>
        <tr>
          <td>User Name:</td>
          <td><input type="text" name="userName"
            value="${fn:escapeXml(param.userName)}">
          </td>
          <td>${fn:escapeXml(userNameError)}</td>
        </tr>
        <tr>
          <td>Password:</td>
          <td><input type="text" name="password"
            value="${fn:escapeXml(param.password)}">
          </td>
          <td>${fn:escapeXml(passwordError)}</td>
        </tr>
        <tr>
          <td>First Name:</td>
          <td><input type="text" name="firstName"
            value="${fn:escapeXml(param.firstName)}">
          </td>
          <td>${fn:escapeXml(firstNameError)}</td>
        </tr>
        <tr>
          <td>Last Name:</td>
          <td><input type="text" name="lastName"
            value="${fn:escapeXml(param.lastName)}">
          </td>
          <td>${fn:escapeXml(lastNameError)}</td>
        </tr>
        <tr>
          <td>Department:</td>
          <td><input type="text" name="dept"
            value="${fn:escapeXml(param.dept)}">
          </td>
          <td>${fn:escapeXml(deptError)}</td>
        </tr>
        <tr>
          <td>Employment Date:</td>
          <td><input type="text" name="empDate"
            value="${fn:escapeXml(param.empDate)}">
          </td>
          <td>${fn:escapeXml(empDateError)}</td>
          <td>(Use format yyyy-mm-dd)</td>
        </tr>
        <tr>
          <td>Email Address:</td>
          <td><input type="text" name="emailAddr"
            value="${fn:escapeXml(param.emailAddr)}">
          </td>
          <td>${fn:escapeXml(emailAddrError)}</td>
          <td>(Use format name@company.com)</td>
        </tr>
        <tr>
          <td colspan=2><input type="submit" value="Submit"></td>
        </tr>
      </table>
    </form>

  </body>
</html>
