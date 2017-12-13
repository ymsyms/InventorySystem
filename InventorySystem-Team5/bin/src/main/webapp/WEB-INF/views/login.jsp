<%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
  <h4>Sign In System</h4>

<form:form commandName="user" method="POST"
	action="${pageContext.request.contextPath}/login"
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
			<input type="submit" value="Sign In" class="btn btn-default"/>
		</div>
	</div>
</form:form>
</html> --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 600px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>
	<div id="login-box">

		<h3>Login with Username and Password</h3>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm' action="<c:url value='/login' />" method='POST'
			class="form-horizontal">

			<div class="form-group">
				<label for="userId" class="control-label col-sm-2">User:</label> <input
					type='text' name='userId' class="form-control">
			</div>
			<div class="form-group">

				<label for="userId" class="control-label col-sm-2">Password:</label>
				<input type='password' name='password' class="form-control">
			</div>
			<div class="form-group">
				<input name="submit" type="submit" value="submit"
					class="btn btn-default col-sm-2" style="margin-left:17%;" />
			</div>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>

</body>
</html>