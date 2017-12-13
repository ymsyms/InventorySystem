<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<nav class="navbar navbar-inverse navbar-static-top example6">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand">Stocklist - Inventory Management System </a>
		</div>
		<c:if test="${not empty sessionScope.USERSESSION}">
			<div id="navbar6" class="navbar-collapse collapse">
				<c:url value="/logout?${_csrf.parameterName}=${_csrf.token}" var="logoutUrl" />
				<form action="${logoutUrl}" method="post" id="logoutForm">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
				<script>
					function formSubmit() {
						document.getElementById("logoutForm").submit();
					}
				</script>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="/inventory/product/list">Home</a></li>
					<li><a href="/inventory/transaction/usageSummary">RecordUsage</a></li>
					<li><a href="/inventory/admin/user/list">User</a></li>
					<li><a href="/inventory/admin/supplier/list">Supplier</a></li>
					<li><a href="/inventory/report/generate">Report</a></li>
					<li><a href="javascript:formSubmit()">SignOut</a></li>
				</ul>
			</div>
		</c:if>
		<!--/.nav-collapse -->
	</div>
	<!--/.container-fluid -->
</nav>
