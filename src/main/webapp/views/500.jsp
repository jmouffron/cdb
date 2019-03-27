<%@include file="/views/imports/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<!-- Bootstrap -->
<%@include file="/views/imports/css.jsp" %>
</head>
<body>
	<%@include file="/views/imports/header.jsp" %>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				Error 500: An error has occured! <br />
				<!-- stacktrace -->
				${stackTrace}
			</div>
		</div>
	</section>

	<%@include file="/views/imports/js.jsp" %>

</body>
</html>