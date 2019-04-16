<%@include file="/views/imports/taglibs.jsp"%>
<%@page info="Computer database | Edit Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database | Edit Computer</title>
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
					<div class="label label-default pull-right">id:
						${computer.id}</div>
					<h1>Edit Computer</h1>

					<form:form action="${ctx}/editComputer" method="POST"
						modelAttribute="dto">
						<form:input path="id" type="hidden"
							value="${computer.id}" id="${computer.id}" />

						<fieldset>
							<div class="form-group">
								<form:label path="name" for="computerName"><spring:message code="editComputer.computer_name" /></form:label>
								<form:input path="name" type="text"
									class="form-control" id="computerName"
									placeholder="Computer name" value="${computer.name}" />
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced"><spring:message code='editComputer.introduced_name' /></form:label>
								<form:input path="introduced" type="date"
									class="form-control" id="introduced"
									placeholder="Introduced date" value="${computer.introduced}" />
							</div>
							<div class="form-group">
								<form:label path="discontinued" for="discontinued"><spring:message code='editComputer.discontinued_date' /></form:label>
								<form:input path="discontinued" type="date"
									class="form-control" id="discontinued"
									placeholder="Discontinued date"
									value="${computer.discontinued}" />

							</div>
							<div class="form-group">
								<form:label path="companyName" for="companyId"><spring:message code='editComputer.company' /></form:label>
								<form:select path="companyName" class="form-control"
									id="companyId" items="${companies}">
									<form:option value="${companies.id}" items="${companies}" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code='editComputer.edit' />" class="btn btn-primary">
							or <a href="${ctx}/dashboard" class="btn btn-default"><spring:message code='editComputer.cancel' /></a>
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