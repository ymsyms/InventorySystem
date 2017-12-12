<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
  <h4>Sign In System</h4>
<form:form modelAttribute="user" method="POST"
	action="${pageContext.request.contextPath}/user/login"
	class="form-horizontal loginForm">
	<div class="form-group">
		<label for="userId" class="control-label col-sm-2"> <spring:message
				code="user.enterUserId" /></label>
		<form:input path="userId" size="40" class="form-control" />
		<form:errors path="userId" cssStyle="color: red;" />
	</div>
	<div class="form-group">
		<label for="password" class="control-label col-sm-2"> <spring:message
				code="user.enterUserPassword" /></label>
		<form:password path="password" size="40" class="form-control" />
		<form:errors path="password" cssStyle="color: red;" />
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<form:button name="submit" type="submit" value="s"
				class="btn btn-default">Sign In
				</form:button>
		</div>
	</div>
</form:form>
</html>