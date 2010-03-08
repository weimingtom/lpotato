<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/mytags" %>

<%-- Create test data --%>
<c:set var="message">
  This is just a lot of text that the browser will format to
  fit the browser window. Attempts to <blink> add HTML elements
  are dealt with by conversion to character entities.
  [code]
  This part I want the browser to leave alone, so that
  all my indentations are left intact:

    public class Foo {
      public String getBar() {
        return bar;
      }
    }
  [/code]
  And then some regular text again.
</c:set>
<html>
  <head>
    <title>Online Forum</title>
  </head>
  <body bgcolor="white">
    <h1>Online Forum</h1>
    Here's a formatted message:
    <p>
      <my:htmlFormat>
        ${message}
      </my:htmlFormat>
    </p>
  </body>
</html>

