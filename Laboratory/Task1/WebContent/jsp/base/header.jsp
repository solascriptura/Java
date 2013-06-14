<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="bean" uri="/WEB-INF/tld/struts-bean.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/tld/struts-html.tld"%>

<fmt:setBundle basename="resource.AppResources"/>
<h1>
	<fmt:message key="header.title"/>
</h1>
<bean:define id="enLanguage" value="en"/>
<bean:define id="ruLanguage" value="ru"/>
<div class="choose_language">
	<html:link paramId="language" paramName="ruLanguage"
		action="/changeLanguage">
		<fmt:message key="header.russian"/>
	</html:link>
</div>
<div class="choose_language">
	<html:link paramId="language" paramName="enLanguage"
		action="/changeLanguage">
		<fmt:message key="header.english"/>
	</html:link>
</div>
