<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
	<fmt:setBundle basename="resource.ApplicationResources"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="jsp.result.title"/></title>
    </head>
    <body>
    	<b><fmt:message key="jsp.result.text.explanation"/></b>
    	<fmt:message var="notInStock" key="jsp.result.text.not.in.stock"/>
    	<fmt:setBundle basename="resource.Config"/>
    	<fmt:message var="datePattern" key="pattern.date.issue"/>
    	<table style="margin-top:20px;">
    		<c:forEach var="category" items="${categories}">
    			<tr>
    				<td><c:out value="${category.name}"/></td>
    				<c:forEach var="subcategory" items="${category.subcategories}">
    					<table style="margin-left:40px;">
    						<tr>
    							<td><c:out value="${subcategory.name}"/></td>
    							<c:forEach var="product" items="${subcategory.products}">
    								<fmt:formatDate var="date" value="${product.issueDate}" pattern="${datePattern}" scope="page"/>
    								<table style="margin-left:120px;">
    									<tr>
    										<td width="150"><c:out value="${product.name}"/></td>
    										<td width="150"><c:out value="${product.producer}"/></td>
    										<td width="80"><c:out value="${product.model}"/></td>
    										<td width="120"><c:out value="${date}"/></td>
    										<td width="80"><c:out value="${product.color}"/></td>
    										<c:choose>
    											<c:when test="${product.notInStock}">
    												<td width="80"><c:out value="${notInStock}"/></td>
    											</c:when>
    											<c:otherwise>
    												<td width="80"><c:out value="${product.price}"/></td>
    											</c:otherwise>
    										</c:choose>
    									</tr>
    								</table>
    							</c:forEach>
    						</tr>
    					</table>
    				</c:forEach>
    			</tr>
    		</c:forEach>
    	</table>
    	<br>
    	<div align="center">
    	<input type="button" onclick="window.location=''" value="Back to start page">
    	</div>
    </body>
</html>