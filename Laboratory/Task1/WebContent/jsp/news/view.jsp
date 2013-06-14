<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="html" uri="/WEB-INF/tld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/tld/struts-bean.tld"%>

<fmt:setBundle basename="resource.AppResources"/>

<body>
	<div class="path">
		<html:link action="/list">
			<fmt:message key="body.news"/>
		</html:link>
		<fmt:message key="body.news.refer"/>
		<fmt:message key="body.news.view"/>
	</div>
	<bean:define id="newsMessage" name="newsForm" property="newsMessage"/>
	<div class="view_form">
		
		<div class="title">
			<fmt:message key="add.news.title"/>
		</div>
		<div class="value">
			<bean:write name="newsMessage" property="title"/>
		</div>
		
		<div class="title">
			<fmt:message key="add.news.date"/>
		</div>
		<div class="value">
			<bean:write name="newsMessage" property="date"
				formatKey="date.pattern"/>
		</div>
		
		<div class="title">
			<fmt:message key="add.news.brief"/>
		</div>
		<div class="value">
			<bean:write name="newsMessage" property="brief"/>
		</div>
		
		<div class="title">
			<fmt:message key="add.news.content"/>
		</div>
		<div class="value">
			<bean:write name="newsMessage" property="content"/>
		</div>
		
		<div class="buttons_view">
			<table>
				<tr>
					<td>
						<div class="edit_button">
							<html:form action="edit.do">
								<html:hidden property="cancelForwardName" value="view"/>
								<html:submit>
									<fmt:message key="view.news.button.edit"/>
								</html:submit>
							</html:form>
						</div>
					</td>
					<td>
						<div class="delete_button">
							<html:form action="delete.do">
								<html:hidden property="selectedNewsIds" value="${newsMessage.id}"></html:hidden>
								<html:submit onclick='return confirmDelete()'>
									<fmt:message key="view.news.button.delete"/>
								</html:submit>
							</html:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>