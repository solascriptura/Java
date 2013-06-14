<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="resource.ApplicationResources"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="jsp.index.title"/></title>
    </head>
    <body>
    	<b><fmt:message key="jsp.index.text.explanation"/></b>
		<table>
			<tr>
				<td>
					<a href="controller?parser=SAX" style="color:blue;margin-left:20px;">
						<fmt:message key="jsp.index.link.parser.sax"/>
					</a>
				</td>
			</tr>
			<tr>
				<td>
					<a href="controller?parser=DOM" style="color:blue;margin-left:20px;">
						<fmt:message key="jsp.index.link.parser.dom"/>
					</a>
				</td>
			</tr>
			<tr>
				<td>
					<a href="controller?parser=StAX" style="color:blue;margin-left:20px;">
						<fmt:message key="jsp.index.link.parser.stax"/>
					</a>
				</td>
			</tr>
		</table>
    </body>
</html>