
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


	<form:form method="POST" modelAttribute="supplier"
		action="${pageContext.request.contextPath}/report/supplier">

		<h4>Inventory Reorder Report</h4>

		<table>
			<tbody>
				<tr>
					<td>Supplier ID</td>
					<td><form:input path="supplierId" /></td>
				</tr>
				<tr>

					<td><input type="submit" value="Generate Report"
						class="blcass" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<c:if test="${fn:length(pSupplierList) gt 0}">
			<table>
				<thead>
					<tr>
						<th>Unit Price</th>
						<th>Min Order Qty</th>						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="report" items="${pSupplierList}">
						<tr class="listRecord">
							<td align="left">${report.unitPrice}</td>
							<td align="left">${report.minimumReorderQty}</td>
					</c:forEach>
				</tbody>
			</table>
			<table>
				<thead>
					<tr>
						<th>Qty on hand</th>
						<th>Reorder Lvl</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="report" items="${productList}">
						<tr class="listRecord">
							<td align="left">${report.availableQty}</td>
							<td align="left">${report.reorderLevel}</td>
					</c:forEach>
				</tbody>
			</table>
			<table>
				<thead>
					<tr>
						<th>Part No</th>
						<th>Order Qty</th>
						<th>Price Qty</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="report" items="${productList}">
						<tr class="listRecord">
							<td align="left">${report.availableQty}</td>
							<td align="left">${report.reorderLevel}</td>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</form:form>
</body>
</html>