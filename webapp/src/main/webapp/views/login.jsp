<%@ include file="imports/taglibs.jsp"%>
<%@ page info="Login Page | Computer database"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="login.heading" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="utf-8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- Bootstrap -->
<%@ include file="imports/css.jsp"%>
<%@ include file="imports/favicons.jsp"%>
</head>
<body>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="login.heading" />
					</h1>
					<form:form action="${ctx}/login" method="post"
						modelAttribute="user">
						<fieldset>
							<div class="form-group">
								<form:errors path="*" cssClass="alert-danger" element="div" />
								<p>
									<form:label path="username" for="username">
										<spring:message code="login.username" />
									</form:label>
									<form:input path="username" class="form-control" type="text"
										id="username" name="username"  autofocus="autofocus" />
									<form:errors path="username" cssClass="alert-danger"
										element="div" />
								</p>
								<p>
									<form:label path="password" for="password">
										<spring:message code="login." />
									</form:label>
									<form:input path="password" class="form-control"
										type="password" id="password" name="password" />
									<form:errors path="password" cssClass="alert-danger"
										element="div" />
								</p>
								<p>
									<form:label path="remember" for="remember">
										<spring:message code="login.remember" />
									</form:label>
									<form:checkbox class="form-control" path="remember"
										id="remember" />
								</p>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<button type="submit" class="btn">
									<spring:message code="login.commit" />
								</button>
								<a href="${ctx}"><button class="btn">
										<spring:message code="login.cancel" />
									</button> </a>
							</div>
						</fieldset>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>