<%@include file="imports/taglibs.jsp"%>
<%@page info="Computer database | Delete Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database | Delete Computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
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
					<form:form action="/computer/delete" method="POST">
						<fieldset>
							<legend><spring:message code="deleteComputer.heading" /> </legend>
							<div class="form-group">
								<p>${computer.getName()}</p>
								<p>${computer.getIntroduced()}</p>
								<p>${computer.discontinued()}</p>
								<p>${computer.getCompany().getName()}</p>
							</div>

							<div class="actions pull-right">
								<input type="submit" value="Delete" class="btn btn-primary">
								or <a href="/computer" class="btn btn-default">Cancel</a>
							</div>
						</fieldset>
					</form:form>
				</div>
			</div>
		</div>
	</section>

	<script src="${ctx}/js/jquery.min.js"></script>
	<%@include file="imports/i18n.jsp"%>
</body>
</html>