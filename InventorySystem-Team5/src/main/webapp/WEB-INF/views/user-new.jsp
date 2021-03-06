<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>New User page</h3>
<form:form method="POST" commandName="user"
	action="${pageContext.request.contextPath}/admin/user/create.html">
	<table class="table table-striped">
		<tbody>
			<tr>
				<td><spring:message code="user.userId" /></td>
				<td><form:input path="userId" /></td>
				<td><form:errors path="userId" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td><spring:message code="user.userName" /></td>
				<td><form:input path="userName" /></td>
				<td><form:errors path="userName" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td><spring:message code="user.password" /></td>
				<td><form:input path="password" /></td>
				<td><form:errors path="password" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td><spring:message code="user.userRole" /></td>
				<td><%-- <form:input path="userRole" /> --%>

				<form:select path="userRole">
					<form:option value="NONE" label="--- Select ---" />
					<form:option value="ROLE_ADMIN" label="Admin" />
					<form:option value="ROLE_MECHANIC" label="Mechanic" />
				</form:select>
				</td>
				<td><form:errors path="userRole" cssStyle="color: red;" /></td>
			</tr>

			<tr>
				<td><input type="submit" class="button button2" value="Create" /></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</form:form>