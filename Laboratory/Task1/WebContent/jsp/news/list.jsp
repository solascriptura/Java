<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="bean" uri="/WEB-INF/tld/struts-bean.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/tld/struts-html.tld"%>
<%@ taglib prefix="logic" uri="/WEB-INF/tld/struts-logic.tld"%>

<fmt:setBundle basename="resource.AppResources"/>

<body>
	<div class="path">
		<html:link action="/list">
			<fmt:message key="body.news"/>
		</html:link>
		<fmt:message key="body.news.refer"/>
		<fmt:message key="body.news.list"/>
	</div>
	
	<div class="errors">
	<html:errors />
	</div>
	
	<html:form action="delete.do">
		<logic:iterate id="news" name="newsForm" property="newsList">
			<div class="news_item">
				<div class="news_title">
					<bean:write name="news" property="title"/>
				</div>
				<div class="news_date">
					<bean:write name="news" property="date" formatKey="date.pattern"/>
				</div>
				<div class="news_brief">
					<bean:write name="news" property="brief"/>
				</div>
				<div class="functions">
					<html:link paramId="newsMessage.id" paramName="news"
						paramProperty="id" action="/view">
						<fmt:message key="list.news.view"/>
					</html:link>
					<html:link paramId="newsMessage.id" paramName="news"
						paramProperty="id" action="/edit">
						<html:param name="cancelForwardName" value="list"/>
						<fmt:message key="list.news.edit"/>
					</html:link>
					<html:multibox property="selectedNewsIds" value="${news.id}"/>
				</div>				
			</div>
		</logic:iterate>

		<div class="under_list">
			<c:choose>
				<c:when test="${not empty newsForm.newsList}">
					<html:submit onclick='return validateDelete()'>
						<fmt:message key="list.news.button.delete" />
					</html:submit>
				</c:when>
				<c:otherwise>
					<fmt:message key="List.news.epmty" />
				</c:otherwise>
			</c:choose>
		</div>
	</html:form>
</body>

