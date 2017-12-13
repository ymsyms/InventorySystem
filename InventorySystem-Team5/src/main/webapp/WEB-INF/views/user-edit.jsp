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

<h3>Edit User page</h3>

<a href="${pageContext.request.contextPath}/admin/user/list">Back To
	User List</a>
<form:form method="POST" modelAttribute="user"
	action="${pageContext.request.contextPath}/admin/user/edit/${user.userId}.html">
	<table class="table table-striped">
		<tbody>
			<tr>
				<td>User Id</td>
				<td><form:input path="userId" readonly="true" /></td>
			</tr>
			<tr>
				<td>User Name</td>
				<td><form:input path="userName" /></td>
				<%-- 				<td><form:errors path="userName" cssStyle="color: red;" /></td> --%>
			</tr>
			<tr>
				<td>Password</td>
				<td><form:input path="password" /></td>
				<%-- 				<td><form:errors path="password" cssStyle="color: red;" /></td> --%>
			</tr>
			<tr>
				<td>User Role</td>
				<td>
					<%-- <form:input path="userRole" /> --%> <form:select
						path="userRole">
						<form:option value="NONE" label="--- Select ---" />
						<form:option value="ROLE_ADMIN" label="Admin" />
						<form:option value="ROLE_MECHANIC" label="Mechanic" />
					</form:select>
				</td>
				<%-- 				<td><form:errors path="userRole" cssStyle="color: red;" /></td> --%>
			</tr>
			<tr>
				<td>userStatus</td>
				<td><form:input path="userStatus" /></td>
				<%-- 				<td><form:errors path="userStatus" cssStyle="color: red;" /></td> --%>
			</tr>




			<tr>

				<td><input type="submit" value="Update" class="blcass" /></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>

</form:form>