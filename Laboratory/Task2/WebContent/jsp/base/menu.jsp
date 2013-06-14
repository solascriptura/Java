<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="html" uri="/WEB-INF/tld/struts-html.tld"%>

<fmt:bundle basename="resource.AppResources">
	<div class="menu_header">
		<h2>
			<fmt:message key="menu.title"/>
		</h2>
	</div>
	<div class="menu_content">
		<ul>
			<li>
				<html:link action="/list">
					<fmt:message key="menu.news.list"/>
				</html:link>
			</li>
			<li>
				<html:link action="/add">
					<html:param name="cancelForwardName" value="list"/>
					<fmt:message key="menu.news.add"/>
				</html:link>
			</li>
		</ul>
	</div>
</fmt:bundle>