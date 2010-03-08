<%@ page language="java" contentType="text/html" %>

<html>
  <head>
    <title>Project Billboard</title>
  </head>
  <body bgcolor="white">

    <form action="storeMsg.do" method="post">

      <table>
        <tr>
          <td>Project:</td>
          <td>
            <select name="category">
              <option>JSP
              <option>Servlet
              <option>EJB
            </select>
          </td>
        </tr>
        <tr>
          <td colspan=2>
            <textarea name="msg" cols="50" rows="10"></textarea>
          </td>
        </tr>
      </table>
      <input type="submit" value="Post Message">
    </form>
  </body>
</html>
