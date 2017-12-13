<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usage Summary</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		$(".spinner").spinner({
			min : 0
		});
	});
</script>

<script type="text/javascript">
	
<%-- To allow the different buttons to do a post request to different controller methods--%>
	function OnSubmitForm() {
		if (document.pressed == 'Update') {
			document.usageSummary.action = "/inventory/transaction/cartUpdate?${_csrf.parameterName}=${_csrf.token}";
		} else if (document.pressed == 'Delete') {
			document.usageSummary.action = "/inventory/transaction/cartRemove?${_csrf.parameterName}=${_csrf.token}";
		} else if (document.pressed == 'Clear List') {
			document.usageSummary.action = "/inventory/transaction/cartClear?${_csrf.parameterName}=${_csrf.token}";
		} else if (document.pressed == 'Submit') {
			document.usageSummary.action = "/inventory/transaction/submitUsage?${_csrf.parameterName}=${_csrf.token}";
		}
		return true;
	}
</script>
<style type="text/css">
.fakeLabel {
	background: rgba(0, 0, 0, 0);
	border: none;
	text-align: center;
}
</style>
</head>
<body>
	<form name="usageSummary" method="post"
		onsubmit="return OnSubmitForm();">
		<h3>Usage Summary</h3>
		<c:choose>
			<c:when test='${ fn:length(usageSummary) > 0}'>
				<%-- Might be redundant, pending removal
				<input type="hidden" id="size" value="${ fn:length(usageSummary)}" />
				--%>
				<table class="table table-striped">
					<tr>
						<th>Delete</th>
						<th>Part no.</th>
						<th>Usage Quantity</th>						
						<th>Available Quantity</th>						
						
						<th>Change Usage To</th>
					</tr>
					<c:forEach var="product" items="${usageSummary}"
						varStatus="mapIndex">
						<tr>
							<td><input type="checkbox" id="chk${mapIndex.index}"
								name="chk${mapIndex.index}" /></td>
							<td>${product.key.partNo}<input type="hidden" 
								id="partNo${mapIndex.index}" name="partNo${mapIndex.index}"
								value="${product.key.partNo}" /></td>
							<td>${product.value}<input type="hidden" 
								value="${product.value}" /></td>							
							<td>${product.key.availableQty}<input type="hidden"  
								id="stock${mapIndex.index}" value="${product.key.availableQty}" />
							</td>
							
							<td><input class="spinner" value="${product.value}"
								style="text-align: center" id="qty${mapIndex.index}"
								name="qty${mapIndex.index}" /></td>
						</tr>
					</c:forEach>
				</table>
				<br />
				<input type="submit" class="button button2"
					onclick="document.pressed=this.value" value="Update" />
				<input type="submit" class="button button2"
					onclick="document.pressed=this.value" value="Delete" />
				<input type="submit" class="button button2"
					onclick="document.pressed=this.value" value="Clear List" />
				<span style="color: red;">${ qtyErrorMessage }</span>
				<br />
				<br />

				<label for="custName">Customer Name:</label>
				<input type="text" name="custName" id="custName" />
				<span style="color: red;">${ nameErrorMessage }</span>
				<input type="submit" class="button button2" 
					onclick="document.pressed=this.value" value="Submit" />
			</c:when>
			<c:otherwise>
				<p>No items have been added yet.</p>
			</c:otherwise>
		</c:choose>
	</form>
</body>
</html>