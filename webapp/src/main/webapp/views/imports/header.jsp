<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="${ctx}/computer"> <spring:message
				code="dashboard.title" />
		</a>
		<div class="navbar-brand navbar-right text-center">
			<label><spring:message code="dashboard.lang" /></label> <select
				id="locales">
				<option value=""></option>
				<option value="en">EN</option>
				<option value="fr">FR</option>
			</select>
		</div>
		<c:choose>
			<c:when test="${ user.isAuthenticated() }">
				<div class="navbar-brand navbar-right text-center">
					<a  class="form-control btn btn-danger" href="${ctx}/logout" title="Logout button">Logout</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="navbar-brand navbar-right text-center">
					<a  class="form-control btn btn-info" href="${ctx}/login" title="Login button">Login</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

</header>