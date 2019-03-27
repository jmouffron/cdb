<%@include file="/views/imports/taglibs.jsp" %>
<%@page info="Computer database | Delete Computer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database | Delete Computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<!-- Bootstrap -->
<%@include file="/views/imports/css.jsp" %>
<%@include file="/views/imports/favicons.jsp" %>
</head>
<body>
	<%@include file="/views/imports/header.jsp" %>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Delete Computer</h1>
					<form action="/deleteComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<p>${computer}</p>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Delete" class="btn btn-primary">
							or <a href="/dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	
</body>
</html>