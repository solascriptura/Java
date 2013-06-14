<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${newsForm.language}" scope="session"/>

<fmt:setBundle basename="resource.AppResources"/>

<script type="text/javascript" src="javascript/jsMessages.jsp"></script>
<script type="text/javascript" src="javascript/functions.js"></script>

<html>
	<head>
		<link href="style/style.css" rel="stylesheet" type="text/css"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="news.title"/></title>
	</head>
	<body>
		<div id="layout">
			<div class="header">
				<tiles:insert attribute="header"/>
			</div>
			<div class="menu">
				<tiles:insert attribute="menu"/>
			</div>
			<div class="body">
				<tiles:insert attribute="body" ignore="true"/>
			</div>
			<div class="footer">
				<tiles:insert attribute="footer"/>
			</div>
		</div>
	</body>
</html>