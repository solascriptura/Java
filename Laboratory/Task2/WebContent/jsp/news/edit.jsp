<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="bean" uri="/WEB-INF/tld/struts-bean.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/tld/struts-html.tld"%>

<fmt:setBundle basename="resource.AppResources"/>

<body>
	<div class="path">
		<html:link action="/list">
			<fmt:message key="body.news"/>
		</html:link>
		<fmt:message key="body.news.refer"/>
		<fmt:message key="body.news.add"/>
	</div>
	
	<fmt:message var="datePattern" key="date.pattern"/>
	<c:choose>
		<c:when test="${empty newsForm.newsMessage.date}">
			<jsp:useBean id="currentDate" class="java.util.Date"/>
			<fmt:formatDate var="date" value="${currentDate}"
				pattern="${datePattern}" scope="page"/>
		</c:when>
		<c:otherwise>
			<fmt:formatDate var="date" value="${newsForm.newsMessage.date}"
				pattern="${datePattern}" scope="page"/>
		</c:otherwise>
	</c:choose>
	
	<jsp:setProperty name="newsForm" property="datePattern" value="${datePattern}"/>
	
	<div class="errors">
	<html:errors />
	</div>
	
	<div class="edit_form" >
		<html:form action="save.do">
			<div class="edit_title">
				<fmt:message key="add.news.title"/>
			</div>
			<div class="edit_value">
				<html:text property="newsMessage.title" size="100" maxlength="100" style="width:600px;"
					styleId="titleField"></html:text>
			</div>
			<div class="edit_title">
				<fmt:message key="add.news.date"/>
			</div>
			<div class="edit_value">
				<html:text property="newsDate" size="10" maxlength="10" style="width:100px;"
					value="${date}" styleId="dateField"></html:text>
			</div>
			<div class="edit_title">
				<fmt:message key="add.news.brief"/>
			</div>
			<div class="textarea">
				<html:textarea property="newsMessage.brief" style="height:100px;width:600px;overflow:scroll;"
					rows="7" styleId="briefField"></html:textarea>
			</div>

			<div class="edit_title">
				<fmt:message key="add.news.content"/>
			</div>
			<div class="textarea">
				<html:textarea property="newsMessage.content" style="height:200px;width:600px;overflow:scroll;"
					rows="10" styleId="contentField"></html:textarea>
			</div>
			<div class="buttons_edit">
				<html:submit onclick='return validateSave()'>
					<fmt:message key="add.news.button.save"/>
				</html:submit>

				<input type="button" onclick="window.location='cancel.do'"
					value="<fmt:message key="add.news.button.cancel"/>"/>
			
			</div>
		</html:form>

	</div>
</body>
</html>
