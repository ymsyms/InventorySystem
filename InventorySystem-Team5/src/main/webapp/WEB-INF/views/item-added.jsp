<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Success</h3>
	<p>${ addingStatusMessage }</p>
	<a href="${pageContext.request.contextPath}/product/list" class="button button1"/>Back To Product List</a>
	<a href="${pageContext.request.contextPath}/transaction/usageSummary" class="button button2"/>Check Out</a>
</body>
</html>