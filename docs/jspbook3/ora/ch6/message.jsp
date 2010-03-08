<html>
  <head>
    <title>Messages of the Day</title>
  </head>
  <body bgcolor="white">
    <h1>Messages of the Day</h1>

    <jsp:useBean id="msg" 
      class="com.ora.jsp.beans.motd.MixedMessageBean" />

    <h2>Deep Thoughts - by Jack Handey</h2>

    <jsp:setProperty name="msg" property="category"
      value="thoughts" />

    <i>
      <jsp:getProperty name="msg" property="message" />
    </i>

    <h2>Quotes From the Famous and the Unknown</h2>

    <jsp:setProperty name="msg" property="category"
      value="quotes" />

    <i>
      <jsp:getProperty name="msg" property="message" />
    </i>

  </body>
</html>
