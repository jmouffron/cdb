<%@include file="imports/taglibs.jsp"%>
<%@page info="Computer database | Dashboard"%>
<!DOCTYPE html>
<html lang="${lang}">
<head>
<title><spring:message code="dashboard.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="utf-8">
<!-- Bootstrap -->
<%@include file="imports/css.jsp"%>
<%@include file="imports/favicons.jsp"%>
</head>
<body>
	<%@include file="imports/header.jsp"%>
	<%@include file="imports/feedback.jsp"%>
	<section id="main">
		<div class="container">
			<c:choose>
				<c:when test="${computerNumber>0}">
					<h1 id="homeTitle">${computerNumber}
						<spring:message code="dashboard.number_computer" />
					</h1>
				</c:when>
				<c:otherwise>
					<h1 id="homeTitle">
						<spring:message code="dashboard.no_number_computer" />
					</h1>
				</c:otherwise>
			</c:choose>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm"
						action="${ctx}/computer?search=${search}&toOrder=${toOrder}" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<spring:message code='dashboard.filter_placeholder' />"
							value="${search}" /> <input type="submit" id="searchsubmit"
							value="<spring:message code='dashboard.filter' />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${ctx}/computer/add"><spring:message
							code="dashboard.add_computer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="dashboard.edit" /></a>
				</div>
			</div>
		</div>

		<form:form id="deleteForm" action="${ctx}/computer/" method="POST"
			modelAttribute="computersDestroyed">
			<form:input type="hidden" name="ids" value="" path="ids" />
		</form:form>

		<div class="container" style="margin-top: 10px;">
			<c:choose>
				<c:when test="${computerNumber == 0}">
					<p>
						<spring:message code="dashboard.no_filter" />
					</p>
				</c:when>
				<c:otherwise>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="editMode" style="width: 60px; height: 22px;"><input
									type="checkbox" id="selectall" /> <span
									style="vertical-align: top;"> - <a href="#"
										id="deleteSelected" onclick="$.fn.deleteSelected();"> <em
											class="fa fa-trash-o fa-lg"></em>
									</a>
								</span></th>
								<th class="text-center"><spring:message code="dashboard.computer_name" /> <a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=0&order=true"><em
										class="fa fa-caret-down"></em></a><a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=0"><em
										class="fa fa-caret-up"></em></a></th>
								<th class="text-center"><spring:message code="dashboard.introduced_date" /> <a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=1&order=true"><em
										class="fa fa-caret-down"></em></a><a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=1"><em
										class="fa fa-caret-up"></em></a></th>
								<th class="text-center"><spring:message code="dashboard.discontinued_date" />
									<a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=2&order=true"><em
										class="fa fa-caret-down"></em></a><a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=2"><em
										class="fa fa-caret-up"></em></a></th>
								<th class="text-center"><spring:message code="dashboard.company" /> <a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=3&order=true"><em
										class="fa fa-caret-down"></em></a><a
									href="${ctx}/computer?page=${currentPage}&search=${search}&toOrder=3"><em
										class="fa fa-caret-up"></em></a></th>

							</tr>
						</thead>
						<tbody id="results">
							<c:forEach var="computer" items="${computers}"
								varStatus="itemsRow">
								<tr>
									<td class="editMode text-center"><input type="checkbox" name="cb"
										class="cb" value="${computer.id}" /></td>
									<td class="text-center"><a
										href="${ctx}/computer/edit/${computer.id}"
										onclick=""> ${computer.name} </a></td>
									<td class="text-center">${computer.introduced}</td>
									<td class="text-center">${computer.discontinued}</td>
									<td class="text-center">${computer.companyName}</td>
									<form:errors path="items" cssClass="danger" element="td" />
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${ currentPage != 1}">
					<li><a
						href="${ctx}/computer?page=${ currentPage - 1 > 0 ? currentPage -1: 1}&search=${search}&toOrder=${toOrder}&perPage=${perPage}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:forEach var="i" items="${pages}">

					<c:choose>
						<c:when test="${ currentPage != 1 && i == 1  }">
							<li><a
								href="${ctx}/computer?page=${i}&search=${search}&toOrder=${toOrder}&perPage=${perPage}">${i}</a></li>
							<li><a>...</a></li>
						</c:when>
						<c:when test="${ (currentPage - 1 > 1) && i == (currentPage - 1) }">
							<li><a
								href="${ctx}/computer?page=${i}&search=${search}&toOrder=${toOrder}&perPage=${perPage}">${i}</a></li>
						</c:when>
						<c:when test="${i == currentPage}">
							<li class="active"><a
								href="${ctx}/computer?page=${i}&search=${search}&toOrder=${toOrder}&perPage=${perPage}">${i}</a></li>
						</c:when>
						<c:when test="${i == currentPage + 1 }">
							<li>
								<a
									href="${ctx}/computer?page=${i}&search=${search}&toOrder=${toOrder}&perPage=${perPage}">${i}</a>
							</li>
							<li><a>...</a></li>
						</c:when>
						<c:when test="${(totalPages - 1 != currentPage) && (i == totalPages - 1 || i == totalPages)}">
							<li><a
								href="${ctx}/computer?page=${i}&search=${search}&toOrder=${toOrder}&perPage=${perPage}">${i}</a></li>
						</c:when>
					</c:choose>

				</c:forEach>
				<c:if test="${ currentPage + 1 <= totalPages}">
					<li><a
						href="${ctx}/computer?page=${ currentPage + 1 }&search=${search}&perPage=${perPage}&toOrder=${ toOrder }"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a class="btn btn-default"
					href="${ctx}/computer?search=${search}&toOrder=${toOrder}&perPage=IDX_10">10</a>
				<a class="btn btn-default"
					href="${ctx}/computer?search=${search}&toOrder=${toOrder}&perPage=IDX_50">50</a>
				<a class="btn btn-default"
					href="${ctx}/computer?search=${search}&toOrder=${toOrder}&perPage=IDX_100">100</a>
			</div>
		</div>
	</footer>

	<%@include file="imports/js.jsp"%>
	<%@include file="imports/i18n.jsp"%>
</body>
</html>