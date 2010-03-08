<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<html>
  <head>
    <title>Phone List</title>
  </head>
  <body bgcolor="white">

    <c:import url="htmltable.xsl" var="stylesheet" />
    <x:transform xslt="${stylesheet}">
      <?xml version="1.0" encoding="ISO-8859-1"?>
      <employees>
        <employee id="123">
          <first-name>Hans</first-name>
          <last-name>Bergsten</last-name>
          <telephone>310-555-1212</telephone>
        </employee>
        <employee id="456">
          <first-name>Bob</first-name>
          <last-name>Eckstein</last-name>
          <telephone>800-555-5678</telephone>
        </employee>
        <employee id="789">
          <first-name>Paula</first-name>
          <last-name>Ferguson</last-name>
          <telephone>213-555-1234</telephone>
        </employee>
      </employees>
    </x:transform>

  </body>
</html>
