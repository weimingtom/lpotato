<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Online Pizza</title>
    <script language="JavaScript" src="dynamicscript.jsp"></script> 
  </head>
  <body bgcolor="white" 
    onLoad="setList(document.pizza.sels, values[0]);">
    <form name="pizza">
      Please make your pizza order selections below:
      <br>
      <select name="categories" 
        onChange="setList(this.form.sels, values[this.selectedIndex]);">
        <option value="0">Size
        <option value="1">Toppings
      </select>
      <br>
      <select name="sels" size="6">
        <option>
          <c:forEach begin="1" end="25">&nbsp;</c:forEach>
        </option>
      </select>
    </form>
  </body>
</html>
