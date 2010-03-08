<%@ page contentType="text/html" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/mytags" %>

<html>
  <head>
    <title>Messages of the Day</title>
  </head>
  <body bgcolor="white">
    <h1>Messages of the Day</h1>
    <h2>Deep Thoughts - by Jack Handey</h2>
    <i>
      <my:motd category="thoughts" />
    </i>

    <h2>Quotes From the Famous and the Unknown</h2>
    <i>
      <my:motd category="quotes" />
    </i>
  </body>
</html>
