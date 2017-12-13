
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

		<h5>Please select Supplier ID below to generate report</h5>
	<br>
		<table>
			<tbody>
				<tr>
					<td>Supplier ID</td>
					
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				
<%-- 					<td><form:input path="supplierId" /></td> --%>


		<td><input type="text" name="supplierId" required /></td>
		
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><input type="submit" value="Generate Report"
						class="button button2"  /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<br><br>	
		<p style="color: red;">${errorMessage}</p>
			
		
		<c:if test="${fn:length(pSupplierList) gt 0}">
		
		<h5 align="center"><u><b>Inventory Reorder Report for Supplier S${supplierId} </b></u></h5>
		<br><br>
			<div>
			<div class="row divReportHead" >
			<br>
					<div class="col-sm-2">Part Number</div>
					<div class="col-sm-1">Unit Price</div>
					<div class="col-sm-2">Available Quantity</div>
					<div class="col-sm-2">Reorder Level</div>
					<div class="col-sm-2">Minimum Order Quantity</div>
					<div class="col-sm-2">Order Quantity</div>
					<div class="col-sm-1">Price</div>
					<br>&nbsp;&nbsp;
				</div>		
				<div class="row divReportBody" >
					<div class="col-sm-2" >
					<c:forEach var="report" items="${orderDetailIdList}">
						<br>${report.partNo}<br>
					</c:forEach>
					</div>
					<div class="col-sm-1">
					<c:forEach var="report" items="${pSupplierList}">
						<br> $ ${report.unitPrice}<br>
					</c:forEach>
					</div>
					<div class="col-sm-2">
					<c:forEach var="report" items="${productList}">
						<br>${report.availableQty}<br>
					</c:forEach>
					</div>
					<div class="col-sm-2">
					<c:forEach var="report" items="${productList}">
						<br>${report.reorderLevel}<br>
					</c:forEach>
					</div>
					<div class="col-sm-2">
					<c:forEach var="report" items="${pSupplierList}">
						<br>${report.minimumReorderQty}<br>
					</c:forEach>
					</div>
					<div class="col-sm-2">
					<c:forEach var="report" items="${orderDetailList}">
						<br>${report.orderQty}<br>
					</c:forEach>
					</div>
					<div class="col-sm-1">
					<c:forEach var="report" items="${price}">
						<br> $ ${report}<br>
					</c:forEach>
					</div>
					<br>&nbsp;&nbsp;
				</div>	
				<div class="row divReportHead">
				<div class="col-sm-2"></div>
				<br>
					<div class="col-sm-1"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2"></div>
					<div class="col-sm-2">Total</div>
					<div class="col-sm-1">$
					${total}</div>
					<br>&nbsp;&nbsp;
				</div>	
			</div>
			<br>
			<h5 align="center"><b>End of Report </b>	</h5>
		</c:if>
	</form:form>
</body>
</html>