<%@ page contentType="text/html" %>
<%@ taglib prefix="ora" uri="orataglib" %>

<html>
  <head>
    <title>Messages of the Day</title>
  </head>
  <body bgcolor="white">
    <h1>Messages of the Day</h1>
    <h2>Deep Thoughts - by Jack Handey</h2>
    <i>
      <ora:motd category="thoughts" />
    </i>

    <h2>Quotes From the Famous and the Unknown</h2>
    <i>
      <ora:motd category="quotes" />
    </i>
  </body>
</html>
