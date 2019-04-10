<%@include file="/views/imports/taglibs.jsp" %>
<%@page info="Computer database | Add Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database | Add Computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="utf-8">
<!-- Bootstrap -->
<%@include file="/views/imports/css.jsp" %>
<%@include file="/views/imports/favicons.jsp"%>
</head>
<body>
	<%@include file="/views/imports/header.jsp" %>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="${ctx}/addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" value="${ computerName }">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									placeholder="Introduced date" value="${ introduced }">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									placeholder="Discontinued date" value="${ discontinued }">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> 
								<select	class="form-control" id="companyId">
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="${ctx}/dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	
	<script src="${ctx}/js/jquery.min.js"></script>
	<script src="${ctx}/js/validation.js"></script>
	<%@include file="/views/imports/i18n.jsp"%>
</body>
</html>