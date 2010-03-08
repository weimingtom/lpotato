<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%-- 
  Get new XML data if the cached version is older than
  1 hour.
--%>
<c:set var="cachePeriod" value="${60 * 60 * 1000}" />
<jsp:useBean id="now" class="java.util.Date" />
<c:if test="${(now.time - cacheTime) > cachePeriod}">
  <c:import url="http://meerkat.oreillynet.com/?&p=4999&_fl=xml&t=ALL" 
    varReader="xmlSource">
    <x:parse var="doc" xml="${xmlSource}" scope="application" />
  </c:import>
  <c:set var="cacheTime" value="${now.time}" scope="application" />
</c:if>

<html>
  <head>
    <title>O'Reilly News</title>
  </head>
  <body bgcolor="white">
    <h1>O'Reilly News</h1>
    <img src="<x:out select="$doc/meerkat/image/url" />">
    This service is based on the news feed from
    <a href="<x:out select="$doc/meerkat/link" />">
      <x:out select="$doc/meerkat/title" /></a>.
    <p>
    <x:out select="$doc/meerkat/description" />
  
    <%-- 
      Create a list of unique categories present in the XML feed
    --%>
    <jsp:useBean id="uniqueCats" class="java.util.TreeMap" />
    <x:forEach select="$doc/meerkat/story/category" var="category">
      <%-- Need to convert the XPath node to a Java String --%>
      <x:set var="catName" select="string($category)" />
      <c:set target="${uniqueCats}" property="${catName}" value="" />
    </x:forEach>

    <form action="news.jsp">
      Category:
      <select name="selCat">
        <option value="ALL">All
        <c:forEach items="${uniqueCats}" var="current">
          <option value="<c:out value="${current.key}" />"
            <c:if test="${param.selCat == current.key}">
              selected
            </c:if>>
            <c:out value="${current.key}" />
          </option>
        </c:forEach>
      </select>
      <input type="submit" value="Filter">  
    </form>

    <%-- Filter the parsed document based on the selection --%>
    <c:choose>
      <c:when test="${empty param.selCat || param.selCat == 'ALL'}">
        <x:set var="stories" select="$doc//story" />
      </c:when>
      <c:otherwise>
        <x:set var="stories" 
          select="$doc//story[category = $param:selCat]" />
      </c:otherwise>
    </c:choose>

    <%-- Generate a table with data for the selection --%>
    <table>
      <x:forEach select="$stories">
        <tr>
          <x:choose>
            <x:when select="category[. = 'General']">
              <td bgcolor="lightgreen">
            </x:when>
            <x:otherwise>
              <td>
            </x:otherwise>
          </x:choose>
            <a href="<x:out select="link" />">
              <x:out select="title" /></a>
            <br>
            <i><x:out select="timestamp" /></i>:
            <b>Category:</b><x:out select="category" />,
            <b>Reported by:</b><x:out select="channel" />
            <br><x:out select="description" />
          </td>
        </tr>
      </x:forEach>
    </table>
  </body>
</html>
