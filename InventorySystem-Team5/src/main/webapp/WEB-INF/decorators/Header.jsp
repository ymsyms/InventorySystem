<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<nav class="navbar navbar-inverse navbar-static-top example6">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand">Stocklist - Inventory Management System 
				</a>
			</div>
			<c:if test="${not empty sessionScope.USERSESSION}">
			<div id="navbar6" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="#">Product</a></li>
					<li><a href="#">Record Usage</a></li>
					<li><a href="../user/logout">Log Out</a></li>
					<li><a href="#">Users</a></li>
					<li><a href="#">Suppliers</a></li>
				</ul>
			</div>
			</c:if>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>
