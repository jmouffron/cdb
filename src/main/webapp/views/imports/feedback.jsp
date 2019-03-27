<c:if test = "${success != null}">
	<div class="alert alert-success" role="alert">
		${ success }
	</div>
</c:if>
<c:if test = "${danger != null}">
	<div class="alert alert-danger" role="alert">
		${ danger }
	</div>
</c:if>