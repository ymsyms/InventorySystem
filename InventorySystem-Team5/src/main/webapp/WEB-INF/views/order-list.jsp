<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<!-- Head -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.fakeLabel {
	background: rgba(0, 0, 0, 0);
	border: none;
}
.textboxcolour{
	background: rgb(216, 242, 255);
}
</style>

<!-- JavaScript -->
<script type="text/javascript">
	function OnSubmitForm() {
		if (document.pressed == 'Add') {
			document.orderSelection.action = "/inventory/admin/order/add?${_csrf.parameterName}=${_csrf.token}";
		} else if (document.pressed == 'Reset') {
			document.orderSelection.action = "/inventory/admin/order/reset?${_csrf.parameterName}=${_csrf.token}";
		} else if (document.pressed == 'Submit') {
			document.orderSelection.action = "/inventory/admin/order/submit?${_csrf.parameterName}=${_csrf.token}";
		}
	}
</script>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>











<!-- body -->
<body>

	<!-- form -->
	<form id="form1" name="orderSelection" method="post"
		onsubmit="return OnSubmitForm()">

		<h3>Create Order</h3>
		<h5>Below are the items which need to be reordered</h5>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<!-- table -->
		<table
			class="table table-striped table-bordered table-hover table-condensed table"
			width="70%">
			<colgroup>
				<col class="col-md-2" >
				<col class="col-md-5" >
				<col class="col-md-2" >
				<col class="col-md-2" >
				<col class="col-md-1" >
			</colgroup>

			<thead>
				<tr>
					<th>PartNo</th>
					<th>Supplier / UnitPrice / MinOrderQty</th>
					<th>CurrentQty</th>
					<th colspan="2">OrderQty</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="orderItem" items="${OLIST}" varStatus="theCount">
					<tr class="listRecord">

						<!-- column: partNo -->
						<td align="left"><input type="text" class="fakeLabel"
							id="partNo${theCount.index}" name="partNo${theCount.index}"
							value="${orderItem.product.partNo}" readonly="readonly"></td>

						<!-- column: productSupplier -->
						<td><select style="width: 100%" class="textboxcolour"
							name="productSupplier${theCount.index}">
								<c:forEach var="p" items="${orderItem.prodSupList}"
									varStatus="mapIndex">
									<option value="${p.id.supplierId}"
										${p.id.supplierId == orderItem.selectedSupplierId ? 'selected' : ''}>
										${p.id.supplierId} / $${p.unitPrice} / ${p.minimumReorderQty}</option>
								</c:forEach>
						</select></td>

						<!-- column: currentQty -->
						<td align="left"><input type="text" class="fakeLabel" style="width:100px"
							id="currentQty${theCount.index}"
							name="currentQty${theCount.index}"
							value="${orderItem.product.availableQty}" readonly="readonly"></td>

						<!-- column: orderQty -->
						<td align="left"><input type="text" class="textboxcolour" style="width:100px"
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
			<div style="float: left; width: 50%;">
				<p>
					<strong>Add Part</strong>
				</p>
				<!-- partNo input -->
				<select name="addPartNo" class="textboxcolour">
					<c:forEach var="product" items="${PLIST}" varStatus="productIndex">
						<option value="${product.partNo}">
							${product.partDescription} - $${product.carDealer} -
							${product.partNo}</option>
					</c:forEach>
				</select>

				<!-- addButton -->
				<input type="submit" class="button button2"
					onclick="document.pressed=this.value" value="Add"> <br>

				<!-- errorMessage -->
				<p style="color: red;">${errorMessage}</p>
			</div>


			<!-- resetButton  -->
			<div style="float: right">
				<input type="submit" class="button button2"
					onclick="document.pressed=this.value" value="Reset">     
				<!-- submitButton -->
				<input type="submit" class="button button2"
					onclick="document.pressed=this.value" value="Submit"> <br>
			</div>


		</div>
	</form>
</body>
</html>