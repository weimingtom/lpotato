<%@ page language="java" contentType="text/html" %>
<html>
  <head>
    <title>Embedding an applet</title>
  </head>
  <body bgcolor="white">
    <h1>Embedding an applet</h1>
    <jsp:plugin type="applet" code="Clock2.class" 
      codebase="applet" 
      jreversion="1.2" width="160" height="150" >
      <jsp:params>
        <jsp:param name="bgcolor" value="ccddff" />
      </jsp:params>
      <jsp:fallback>
        Plugin tag OBJECT or EMBED not supported by browser.
      </jsp:fallback>
    </jsp:plugin>
  </body>
</html>
