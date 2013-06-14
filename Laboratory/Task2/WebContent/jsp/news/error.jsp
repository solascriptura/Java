<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<fmt:bundle basename="resource.AppResources">
	<html>
		<head>
			<title>
			<fmt:message key="error.page.title" />
			</title>
		</head>
		<body>
			<fmt:message key="error.page.message" />
			<br />
			<html:link action="/init">
				<fmt:message key="error.page.link" />
			</html:link>
		</body>
	</html>
</fmt:bundle>