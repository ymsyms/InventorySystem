<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<form:form modelAttribute="user" method="POST" action="${pageContext.request.contextPath}/user/authenticate" >
	<table class="framed" >
		<tr>
			<td>
			<spring:message code="user.userId" /></td>
			<td colspan="3"><form:input path="userId" size="40" /></td>
		</tr>
		<tr>
			<td><spring:message code="user.password" /></td>
			<td colspan="3"><form:password path="password" size="40" /></td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
			<form:button name="submit" type="submit" value="s">
					<img src="${pageContext.request.contextPath}/image/button_login.gif" alt="" align="middle">
				</form:button></td>
			<td>
			<form:button name="clear" type="reset" value="r">
					<img src="${pageContext.request.contextPath}/image/button_clear.gif" alt="" align="middle">
				</form:button>
				</td>
		</tr>
	</table>
</form:form>
</html>