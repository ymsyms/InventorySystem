<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<c:url value="/css/style.css" var="style" />
<link rel="STYLESHEET" type="text/css" href="${style}" />
<!-- Bootstrap CSS -->
<c:url value="/css/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	var="bootstrap" />
<link rel="stylesheet" href="${bootstrap}">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%@include file="Header.jsp"%>
<dec:head />
</head>
<body>
	<div class="container">
		<dec:body />
	</div>
	<div id="footer" align="center">
		<hr>
		<small> &copy; ISS Team5 Inventory System 2017 </small>
	</div>
</body>
</html>
