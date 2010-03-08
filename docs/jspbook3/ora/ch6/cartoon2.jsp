<html>
  <head>
    <title>A dose of Dilbert</title>
  </head>
  <body bgcolor="white">
    <h1>A dose of Dilbert</h1>

    <jsp:useBean id="cartoon" 
      class="com.ora.jsp.beans.motd.CartoonBean" />

    <img src="images/${cartoon.fileName}">

  </body>
</html>
