
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.fakeLabel {
	background: rgba(0, 0, 0, 0);
	border: none;
}
</style>

<!-- JavaScript -->
<script type="text/javascript">
	function OnSubmitForm() {
		if (document.pressed == 'Add') {
			document.orderSelection.action = "/inventory/admin/order/add";
		} else if (document.pressed == 'Reset') {
			document.orderSelection.action = "/inventory/admin/order/reset";
		} else if (document.pressed == 'Submit') {
			document.orderSelection.action = "/inventory/admin/order/submit";
		}
	}
</script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<!-- body -->
<body>

	<!-- form -->
	<form id="form1" name="orderSelection" method="post"
		onsubmit="return OnSubmitForm()">

		<h3>New Order Page</h3>

		<!-- table -->
		<table class="table-condensed" width="70%">
			<colgroup>
				<col span="1" style="width: 10%;">
				<col span="1" style="width: 50%;">
				<col span="1" style="width: 10%;">
				<col span="1" style="width: 10%;">
				<col span="1" style="width: 10%;">
			</colgroup>

			<thead>
				<tr>
					<th style="">PartNo</th>
					<th>Supplier / UnitPrice / MinOrderQty</th>
					<th>CurrentQty</th>
					<th>OrderQty</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="orderItem" items="${ORDERLIST}" varStatus="theCount">
					<tr class="listRecord">

						<!-- column: partNo -->
						<td align="left"><input type="text" class="fakeLabel"
							id="partNo${theCount.index}" name="partNo${theCount.index}"
							value="${orderItem.product.partNo}" readonly="readonly"></td>

						<!-- column: productSupplier -->
						<td><select style="width:100%" name="productSupplier${theCount.index}">
								<c:forEach var="p" items="${orderItem.prodSupList}"
									varStatus="mapIndex">
									<option value="${p.id.supplierId}"
										${p.id.supplierId == orderItem.selectedSupplierId ? 'selected' : ''}>
										${p.id.supplierId} / $${p.unitPrice} / ${p.minimumReorderQty}</option>
								</c:forEach>
						</select></td>

						<!-- column: currentQty -->
						<td align="left"><input type="text" class="fakeLabel"
							id="currentQty${theCount.index}"
							name="currentQty${theCount.index}"
							value="${orderItem.product.availableQty}" readonly="readonly"></td>

						<!-- column: orderQty -->
						<td align="left"><input type="text"
							id="orderQty${theCount.index}" name="orderQty${theCount.index}"
							value="${orderItem.quantity}"></td>

						<!-- column: Delete -->
						<td><a
							href="${pageContext.request.contextPath}/admin/order/delete/${orderItem.product.partNo}">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- Input -->
		<div>
			<!-- partNo input -->
			<input type="text" name="partNo">

			<!-- addButton -->
			<input type="submit" class="button"
				onclick="document.pressed=this.value" value="Add"> <br>

			<!-- errorMessage -->
			<p style="color: red;">${errorMessage}</p>

			<!-- resetButton  -->
			<input type="submit" class="button"
				onclick="document.pressed=this.value" value="Reset"> <br>

			<!-- submitButton -->
			<input type="submit" class="button"
				onclick="document.pressed=this.value" value="Submit"> <br>
		</div>
	</form>
</body>
</html>