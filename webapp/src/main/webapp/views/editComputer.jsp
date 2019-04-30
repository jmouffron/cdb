<%@include file="imports/taglibs.jsp"%>
<%@page info="Computer database | Edit Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database | Edit Computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="utf-8">
<!-- Bootstrap -->
<%@include file="imports/css.jsp"%>
<%@include file="imports/favicons.jsp"%>
</head>
<body>
	<%@include file="imports/header.jsp"%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${computer.id}</div>
					<h1><spring:message code="editComputer.heading" /></h1>

					<form:form action="${ctx}/computer/edit/${computer.id}" method="POST"
						modelAttribute="dto">
						<form:input path="id" type="hidden" value="${computer.id}"
							id="${computer.id}" />

						<fieldset>
							<form:errors path="*" cssClass="errorblock" element="div" />
							<div class="form-group">
								<form:label path="name" for="computerName">
									<spring:message code="editComputer.computer_name" />
								</form:label>
								<form:errors path="name" cssClass="errorblock" element="div" />
								<form:input path="name" type="text" class="form-control"
									id="computerName"
									placeholder="<spring:message code='editComputer.computer_name' />"
									value="${computer.name}" />
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced">
									<spring:message code='editComputer.introduced_date' />
								</form:label>
								<form:errors path="introduced" cssClass="errorblock" element="div" />
								<form:input path="introduced" type="date" class="form-control"
									id="introduced"
									placeholder="<spring:message code='editComputer.introduced_date' />"
									value="${computer.introduced}" />
							</div>
							<div class="form-group">
								<form:label path="discontinued" for="discontinued">
									<spring:message code='editComputer.discontinued_date' />
								</form:label>
								<form:errors path="discontinued" cssClass="errorblock" element="div" />
								<form:input path="discontinued" type="date" class="form-control"
									id="discontinued"
									placeholder="<spring:message code='editComputer.discontinued_date' />"
									value="${computer.discontinued}" />

							</div>
							<div class="form-group">
								<form:label path="companyId" for="companyId">
									<spring:message code='editComputer.company' />
								</form:label>
								<form:select path="companyId" class="form-control" id="companyId">
									<form:option value="${ computer.companyId }" selected="selected">${ computer.companyName }</form:option>
									<form:options itemValue="id" itemLabel="name" items="${companies}" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code='editComputer.edit' />"
								class="btn btn-primary"> or <a href="${ctx}/computer"
								class="btn btn-default"><spring:message
									code='editComputer.cancel' /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>

	<script src="${ctx}/js/jquery.min.js"></script>
	<script src="${ctx}/js/validation.js"></script>
	<%@include file="imports/i18n.jsp"%>
</body>
</html>