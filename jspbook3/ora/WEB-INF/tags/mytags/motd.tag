<%@ tag body-content="empty" %>
<%@ attribute name="category" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="mmb" class="com.ora.jsp.beans.motd.MixedMessageBean" />
<c:set target="${mmb}" property="category" value="${category}" />
${mmb.message}
