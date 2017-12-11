
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h3>Product List Page</h3>	

	<form:form modelAttribute="product" method="POST" action="${pageContext.request.contextPath}/product/search" >
	<table>
	<tr>
	<td><spring:message code="product.partNo" /></td>
	<td><form:input path="partNo" size="40" /></td>
	</tr>
	
	</table>
	
	<form:button name="submit" type="submit" value="Search"><img src="${pageContext.request.contextPath}/image/button_submit.gif" alt="" align="middle">
	</form:button>
</form:form>

	<a href="${pageContext.request.contextPath}/product/create">Add
		Product</a>
	<c:if test="${fn:length(productList) gt 0}">
		<table>

			<thead>
				<tr>
					<th><spring:message code="product.partNo" /></th>
					<th><spring:message code="product.partNo" /></th>
					<th><spring:message code="product.partNo" /></th>
					<th><spring:message code="product.partNo" /></th>
					<th>Color</th>
					<th>Dimension</th>
					<th>Reorder Level</th>
					<th>Shelf Location</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${productList}">
					<tr class="listRecord">
						<td align="left">${product.partNo}</td>
						<td align="left">${product.carDealer}</td>
						<td align="left">${product.partDescription}</td>
						<td align="left">${product.availableQty}</td>
						<td align="left">${product.color}</td>
						<td align="left">${product.dimension}</td>
						<td align="left">${product.reorderLevel}</td>
						<td align="left">${product.shelfLocation}</td>
						<td align="center"><a
							href="${pageContext.request.contextPath}/product/detail/${product.partNo}.html"><spring:message
									code="caption.detail" /></a></td>
						<td align="center"><a
							href="${pageContext.request.contextPath}/product/edit/${product.partNo}.html"><spring:message
									code="caption.edit" /></a></td>
						<td><a
							href="${pageContext.request.contextPath}/product/delete/${product.partNo}.html"><spring:message
									code="caption.delete" /></a></td>
					</tr>
				</c:forEach>
			</tbody>


		</table>
	</c:if>
</body>
</html>