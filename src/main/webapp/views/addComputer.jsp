<%@include file="/views/imports/taglibs.jsp"%>
<%@page info="Computer database | Add Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="addComputer.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="utf-8">
<!-- Bootstrap -->
<%@include file="/views/imports/css.jsp"%>
<%@include file="/views/imports/favicons.jsp"%>
</head>
<body>
	<%@include file="/views/imports/header.jsp"%>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="addComputer.heading" /></h1>
					<form:form method="POST" action="${ctx}/addComputer" modelAttribute="computer">
						<fieldset>
							<div class="form-group">
								<form:label path="name"><spring:message code="addComputer.computer_name" /></form:label>
								<form:input path="name" class="form-control" id="computerName"
									placeholder="Computer name" value="${ computerName }" />
							</div>
							<div class="form-group">
								<form:label path="introduced"><spring:message code="addComputer.introduced_date" /></form:label> <form:input
									path="introduced" type="date" class="form-control" id="introduced"
									placeholder="Introduced date" value="${ introduced }" />
							</div>
							<div class="form-group">
								<form:label path="discontinued"><spring:message code="addComputer.discontinued_date" /></form:label> <form:input
									path="discontinued" type="date" class="form-control" id="discontinued"
									placeholder="Discontinued date" value="${ discontinued }" />
							</div>
							<div class="form-group">
								<form:label path="companyName"><spring:message code="addComputer.company" /></form:label> 
								<form:select path="companyName"
									class="form-control" id="companyId" items="${companies}">
									<form:option value="${companies.id}" items="${companies}" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code='addComputer.add' />" class="btn btn-primary" />
							or <a href="${ctx}/dashboard" class="btn btn-default"><spring:message code="addComputer.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>

	<script src="${ctx}/js/jquery.min.js"></script>
	<script src="${ctx}/js/validation.js"></script>
	<%@include file="/views/imports/i18n.jsp"%>
</body>
</html>