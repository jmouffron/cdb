<%@ include file="imports/taglibs.jsp"%>
<%@ page info="signup Page | Computer database"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="signup.heading" /></title>
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
						<spring:message code="signup.heading" />
					</h1>
					<form:form action="${ctx}/signup" method="post"
						modelAttribute="user">
						<fieldset>

							<form:errors path="*" cssClass="alert-danger" element="div" />
							<div class="form-group">
								<form:label path="username">
									<spring:message code="signup.username" />
								</form:label>
								<form:input path="username" class="form-control" type="text"
									id="username" name="username" autofocus="autofocus" />
								<form:errors path="username" cssClass="alert-danger"
									element="div" />
							</div>
							<div class="form-group">
								<form:label path="password">
									<spring:message code="signup.password" />
								</form:label>
								<form:input path="password" class="form-control" type="password"
									id="password" name="password" />
								<form:errors path="password" cssClass="alert-danger"
									element="div" />
							</div>
							<div class="actions pull-right">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<button type="submit" class="btn btn-primary">
									<spring:message code="signup.commit" />
								</button>
								<a href="${ctx}"><button class="btn btn-danger">
										<spring:message code="signup.cancel" />
									</button> </a>
							</div>
							<a href="<c:url value='${ctx}/login'/>"><button
									class="btn btn-info">
									<spring:message code="signup.login" />
								</button> </a>
						</fieldset>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>