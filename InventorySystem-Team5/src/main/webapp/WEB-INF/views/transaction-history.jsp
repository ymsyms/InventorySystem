<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	session="true" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction History</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<script>
	$(function() {
		$("#datepicker").datepicker({
			orientation : "bottom auto",
			dateFormat : "yy-mm-dd",
			changeMonth : true,
			changeYear : true
		});
		$("#datepicker1").datepicker({
			orientation : "bottom auto",
			dateFormat : "yy-mm-dd",
			changeMonth : true,
			changeYear : true
		});
	});
</script>

</head>
<body>
	<form:form method="POST" modelAttribute="product"
		action="${pageContext.request.contextPath}/transaction/searchTranHistory/${partNo}">
		<a
			href="${pageContext.request.contextPath}/product/detail/${product.partNo}">Back
			To Product Detail</a>


		<h3>Transaction History</h3>

		<table class="table table-striped">
			<tbody>
				<tr>
					<td><label for="partNo">Part No</label></td>
					<td><form:input type="text" name="partNotxtbx" path="partNo" /></td>
				</tr>
				<tr>
					<td><label for="partDesc">Part Description</label></td>
					<td><form:input type="text" name="partDesctxtbx"
							path="partDescription" /></td>
				</tr>
				<tr>
					<td><label for="tranStartDate">Transaction Start Date</label></td>
					<td><input type="text" id="datepicker" name="sdate"
						value="${sessionScope.sdate}" /></td>
				</tr>
				<tr>
					<td><label for="tranEndDate">Transaction End Date</label></td>
					<td><input type="text" id="datepicker1" name="edate"
						value="${sessionScope.edate}" /></td>
					<span style="color: red;">${ dateErrorMessage }</span>
				</tr>


				<tr>
					<td><input type="submit" name="searchbtn" value="Search" class="button button2"  /></td>
				</tr>

			</tbody>
		</table>

		<p>${ statusMessage }</p>
		<c:choose>
			<c:when test='${fn:length(transHistory) > 0}'>
				<%-- <p>Transaction History between ${sdate} and ${edate}</p> --%>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Transaction ID</th>
							<th>Transaction Date</th>
							<th>Part No</th>
							<th>Quantity</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="trans" items="${transHistory}"
							varStatus="mapIndex">
							<tr>
								<td>${trans.id.transactionId}</td>
								<td>${trnsDates.get(mapIndex.index) }</td>
								<td>${trans.id.partNo}</td>
								<td>${trans.transactionQty}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
		</c:choose>
	</form:form>
</body>
</html>