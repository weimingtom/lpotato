<%@ page contentType="text/plain" %>
<%@ taglib prefix="xmp" uri="xmplib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Correct usage. --%>
<xmp:parent>
  <xmp:child/>
</xmp:parent>

<%-- Incorrect usage. The validator finds and reports these error. --%>
<xmp:child/>
<c:if test="true">
  <xmp:child/>
</c:if>
