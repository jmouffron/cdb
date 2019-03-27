<%@include file="/views/imports/taglibs.jsp"%>
<%@page info="Computer database | Edit Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database | Edit Computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
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
					<div class="label label-default pull-right">id: ${id}</div>
					<h1>Edit Computer</h1>

					<form action="/editComputer" method="POST">
						<input type="hidden" value="0" id="${id}" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" value="${name}">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									placeholder="Introduced date" value="${introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									placeholder="Discontinued date" value="${discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId">
									<c:forEach items="${companies}" var="company">
										<option value="${company.getId()}">${company.getName()}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="/dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src="${ctx}/js/jquery.min.js"></script>
	<script src="${ctx}/js/validation.js"></script>
</body>
</html>