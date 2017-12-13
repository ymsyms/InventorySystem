<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="/resources/demos/external/jquery-mousewheel/jquery.mousewheel.js"></script>
<script>
	$(function() {
		$(".spinner").spinner({
			min : 0
		});
	});
</script>

<script type="text/javascript">
	function OnSubmitForm() {
		if (document.pressed == 'Use this product') {
			document.productDetail.action = "/inventory/product/use?${_csrf.parameterName}=${_csrf.token}";
		} else if (document.pressed == 'Transaction History') {
			document.productDetail.action = "/inventory/transaction/viewTranHistory/${product.partNo}";
		}
		return true;
	}
</script>
<style type="text/css">
.fakeLabel {
	background: rgba(0, 0, 0, 0);
	border: none;
}
</style>

<h3>Product Detail page</h3>
<%-- <form:form method="POST" modelAttribute="product"
	action="${pageContext.request.contextPath}/product/detail/${product.partNo}.html"> --%>
<form name="productDetail" method="POST" onsubmit="return OnSubmitForm()">
<a href="${pageContext.request.contextPath}/product/list">Back To Product List</a>
	<table class="table table-striped">
		<tbody>
			<tr>
				<td><spring:message code="product.partNo" /></td>
				<td><input type="text" name="partNo" readonly="readonly" class="fakeLabel" value="${product.partNo}"/></td>
			</tr>
			<tr>
				<td><spring:message code="product.carDealer" /></td>
				<td>${product.carDealer}</td>
			</tr>
			<tr>
				<td><spring:message code="product.partDescription" /></td>
				<td>${product.partDescription}</td>
			</tr>
			<tr>
				<td><spring:message code="product.availableQty" /></td>
				<td><input type="text" name="availableQuantity" readonly="readonly" class="fakeLabel" value="${product.availableQty}"/></td>
			</tr>
			<tr>
				<td><spring:message code="product.color" /></td>
				<td>${product.color}</td>
			</tr>
			<tr>
				<td><spring:message code="product.dimension" /></td>
				<td>${product.dimension}</td>
			</tr>
			<tr>
				<td><spring:message code="product.reorderLevel" /></td>
				<td>${product.reorderLevel}</td>
			</tr>
			<tr>
				<td><spring:message code="product.shelfLocation" /></td>
				<td>${product.shelfLocation}</td>
			</tr>
			<tr>
				<td><spring:message code="caption.qty" /></td>
				<td><input class="spinner" id="newQuantity" name="newQuantity" /></td>
			</tr>
		</tbody>
	</table>
	
	<input type="submit" onclick="document.pressed=this.value" value="Use this product" />
	<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span>
	<input type="submit" onclick="document.pressed=this.value" value="Transaction History" />
	<span style="color:red;"> ${qtyErrorMessage } </span>
</form>

	<%-- <table>
		<tbody >
			<tr>
			    
				<td><form:form modelAttribute="product" method="POST"
						action="${pageContext.request.contextPath}/product/use">						
						<td><input type="submit" value="Use This Product" class="blcass" /></td>
					</form:form></td>
					<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>
				<td><form:form modelAttribute="product" method="POST"
					action="${pageContext.request.contextPath}/product/use">
					<td><input type="submit" value="Transaction History"
						class="blcass" /></td>
				</form:form></td>
			</tr>
		</tbody>
	</table> --%>

