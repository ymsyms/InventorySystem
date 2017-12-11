<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<h2>Sign In To System </h2>
<form:form modelAttribute="user" method="POST"
	action="${pageContext.request.contextPath}/user/authenticate">
	<div class="form-group">
		<label for="userId"> <spring:message code="user.enterUserId" /></label>
		<form:input path="userId" size="40" class="form-control"/>
	</div>
	<div class="form-group">
		<label for="password"> <spring:message code="user.enterUserPassword" /></label>
		<form:password path="password" size="40" class="form-control"/>
	</div>
	<form:button name="submit" type="submit" value="s"
		class="btn btn-default">Sign In
				</form:button>
</form:form>
</html>