<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Returns</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
  <script>
  $(function() {
	    $("#datepicker1").datepicker({orientation: "bottom auto",
	    	dateFormat: "yy-mm-dd",
	    	changeMonth: true,
	        changeYear: true});	    
	  } );	  
  </script>
  <script>
 function selectionChanged(){
	  $("#partNo").val();
	  var prodid = $("#partNo").val();         
	 	 $('#hdnpartNo').val(prodid);
	 	 $('#partNo').val(prodid);
	    $.ajax({
	        type: "GET",	        	        
	        url: "${pageContext.request.contextPath}/return/getProdDetails?prodId="+prodid,	        		
	        success:function(resp){	        		            	          
	                $('#partDescription').val(resp[0]);
	                $('#carDealer').val(resp[1]);
	         },	        
	        error: function(e){
	            alert("Submit failed");
	        }
	    });
  };
	
  </script>
</head>
<body>
<form:form method="POST" modelAttribute="return" action="${pageContext.request.contextPath}/return/create">
<h3>Returns</h3>
			<br/>
			<br/>
			<table class="table table-striped">					
			  <tr>
				   <td>
				   		<label for="returnDate"  >Return Date</label>
				   </td>
				   <td>
				   		<input type="text" id="datepicker1" name="returnDate"  />
				   		<form:errors path="returnDate" cssStyle="color: red;" />	
				   </td>			  					
			   </tr>
			   <tr>
				   <td>
				   		<label for="partNo" >Part No</label>
				   </td>
				   <td>
				   		<select  id="partNo" name="part" onchange="selectionChanged()" >
				   		<option value="" label="--- Select ---" />
						    <c:forEach items="${productList}" var="product">				   		 
						        <option value="${product.partNo}"><c:out value="${product.partNo}" /></option>
						    </c:forEach>
						    
						</select>
						<input type="hidden" id="hdnpartNo" name="hdnpartNo" value="">
						<%-- <td><form:errors path="partNo" cssStyle="color: red;" /></td> --%>
				   </td>
			   </tr>
			   <tr>
				   <td>
				   		<label for="partDescription">Part description</label>
				   </td>
				   <td>
				   		<input type="text" id="partDescription"  name="partDescription" />
				   </td>
			   </tr>
			   <tr>
				   <td>
				   		<label for="dealer" Class="returnFormLabel">Dealer</label>
				   </td>
				   <td>
				   		<input type="text" id="carDealer" name="carDealer"  />
				   </td>
			   </tr>	
			    <tr>
				   <td>
				   		 <label for="returnQty" Class="returnFormLabel">Return Quantity</label>
				   </td>
				   <td>
				   		<input type="text" id="returnQty" name="returnQty" />
				   		<span style="color:red;">${ returnQtyError }</span>
				   </td>
			   </tr>						
			</table>				
			<input type="submit" name="searchbtn" value="Submit" class="button button2"/>
			<br/>
			<br/>
			<br/>
			</form:form>
</body>
</html>