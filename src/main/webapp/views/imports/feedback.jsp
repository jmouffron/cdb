<c:if test="${success != null}">
	<div class="alert alert-success" id="success" role="alert">
		${ success }
		<a href="#" class="close" data-dismiss="alert" aria-label="close"
				aria-hidden="true">&times;</a>
	</div>
</c:if>
<c:if test="${danger != null}">
	<div class="alert alert-danger" id="danger" role="alert">
		${ danger }
		<a href="#" class="close" data-dismiss="alert" aria-label="close"
				aria-hidden="true">&times;</a>
	</div>
</c:if>