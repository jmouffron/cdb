<%@ include file="imports/taglibs.jsp"%>
<%@ page info="Computer database | Add Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="addComputer.heading" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="utf-8">
<!-- Bootstrap -->
<%@ include file="imports/css.jsp"%>
<%@ include file="imports/favicons.jsp"%>
</head>
<body>
	<%@include file="imports/header.jsp"%>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="addComputer.heading" />
					</h1>
					<form:form method="POST" action="${ctx}/computer/add"
						modelAttribute="computer">
						<fieldset>
							<div class="form-group">
								<form:errors path="*" cssClass="danger" element="div" />
								<form:label path="name">
									<spring:message code="addComputer.computer_name" var="name_placeholder"/>
									${name_placeholder}
								</form:label>
								<form:errors path="name" cssClass="alert-danger" element="div" />
								<form:input path="name" class="form-control" id="computerName"
									placeholder="${name_placeholder}"
									value="${ computerName }" autofocus="autofocus" />
							</div>
							<div class="form-group">
								<form:label path="introduced">
									<spring:message code="addComputer.introduced_date" />
								</form:label>
								<form:errors path="introduced" cssClass="alert-danger" element="div" />
								<form:input path="introduced" type="date" class="form-control"
									id="introduced"
									placeholder="<spring:message code='addComputer.introduced_date' />"
									value="${ introduced }" />
							</div>
							<div class="form-group">
								<form:label path="discontinued">
									<spring:message code="addComputer.discontinued_date" />
								</form:label>
								<form:errors path="discontinued" cssClass="alert-danger" element="div" />
								<form:input path="discontinued" type="date" class="form-control"
									id="discontinued"
									placeholder="<spring:message code='addComputer.discontinued_date' />"
									value="${ discontinued }" />
							</div>
							<div class="form-group">
								<form:label path="companyId" for="companyId">
									<spring:message code='addComputer.company' />
								</form:label>
								<form:select path="companyId" class="form-control"
									id="companyId">
									<form:option value="${ companyId }" selected="selected">
										${ companyName }
									</form:option>
									<form:options itemValue="id" itemLabel="name"
										items="${companies}" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code='addComputer.add' />"
								class="btn btn-primary" /> or <a href="${ctx}/computer"
								class="btn btn-default"><spring:message
									code="addComputer.cancel" /></a>
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