<%@ page language="java" contentType="text/html; charset=ISO-8859-1" session="true"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction History</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

 
<script>
  $(function() {
    $("#datepicker").datepicker({orientation: "bottom auto",
    	dateFormat: "yy-mm-dd",
    	changeMonth: true,
        changeYear: true});
    $("#datepicker1").datepicker({orientation: "bottom auto",
    	dateFormat: "yy-mm-dd",
    	changeMonth: true,
        changeYear: true});  
  } );
  

  </script>

</head>
<body>
<form:form  method="POST" modelAttribute="product"
action="${pageContext.request.contextPath}/transaction/searchTranHistory/${partNo}" >
<h3>Transaction History</h3>
		
			<label for="partNo" style="margin:0 0 10px 0">Part No</label>
			<form:input type="text" name="partNotxtbx" path="partNo" style="margin:50px 20px 30px 100px"/>
			<br/>
				
			<label for="partDesc" style="margin:0 0 10px 0">Part Description</label>
			<form:input type="text" name="partDesctxtbx" path="partDescription" style="margin:0 20px 30px 50px"/>
			<br/>
			
			<label for="tranStartDate" style="margin:0 0 10px 0">Transaction Start Date</label>
			<input type="text" id="datepicker" name="sdate"  style="margin:0 20px 30px 15px" value="${sessionScope.sdate}"/>
			<br/>
			
			<label for="tranEndDate" style="margin:0 0 10px 0">Transaction End Date</label>
			<input type="text" id="datepicker1" name="edate" style="margin:0 20px 30px 20px" value="${sessionScope.edate}"/>
			<span style="color:red;">${ dateErrorMessage }</span>
			<br/>	
			<br/>
			<input type="submit" name="searchbtn" value="Search"/>
			<br/>
			<br/>
			<br/>	
			
			<p>${ statusMessage }</p>	
	<c:choose>	
			<c:when test='${fn:length(transHistory) > 0}'>	
			<%-- <p>Transaction History between ${sdate} and ${edate}</p> --%>	
				<table style="cellspacing: 2; cellpadding: 2; border: 1;">
					<thead>
						<tr>
						<th>Transaction ID</th>
						<th>Transaction Date</th>
						<th>Part No</th>
						<th>Quantity</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="trans" items="${transHistory}" varStatus="mapIndex">
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