<%@include file="imports/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<!-- Bootstrap -->
<%@include file="imports/css.jsp"%>
<%@include file="imports/favicons.jsp"%>
</head>
<body>
	<%@include file="imports/header.jsp"%>

	<section id="main">
		<div class="container-fluid">
			<div class="alert alert-danger">
				Error 400: Bad Request!
			</div>
			<div class="alert alert-danger">
				${stackTrace}
			</div>
		</div>
	</section>
	
	<script src="${ctx}/js/jquery.min.js"></script>
	<%@include file="imports/i18n.jsp"%>
</body>
</html>