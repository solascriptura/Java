<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="resource.ApplicationResources"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="jsp.error.title"/></title>
    </head>
    <body>
    	<b><fmt:message key="jsp.error.text.explanation"/></b><br/>
    	<c:out value="${error}"/>
    </body>
</html>