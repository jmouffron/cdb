<%@include file="/views/imports/taglibs.jsp"%>
<%@page info="Computer database | Dashboard"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="dashboard.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="utf-8">
<!-- Bootstrap -->
<%@include file="/views/imports/css.jsp"%>
<%@include file="/views/imports/favicons.jsp"%>
</head>
<body>
	<%@include file="/views/imports/header.jsp"%>
	<%@include file="/views/imports/feedback.jsp"%>
	<section id="main">
		<div class="container">
			<c:choose>
				<c:when test="${computerNumber>0}">
					<h1 id="homeTitle">${computerNumber} <spring:message code="dashboard.number_computer" /></h1>
				</c:when>
				<c:otherwise>
					<h1 id="homeTitle"><spring:message code="dashboard.no_number_computer" /></h1>
				</c:otherwise>
			</c:choose>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm"
						action="${ctx}/?search=${search}&toOrder=${toOrder}" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code='dashboard.filter_placeholder' />" value="${search}" />
						<input type="submit" id="searchsubmit" value="<spring:message code='dashboard.filter' />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${ctx}/addComputer"><spring:message code="dashboard.add_computer" /></a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<c:choose>
				<c:when test="${computerNumber == 0}">
					<p><spring:message code="dashboard.no_filter" /></p>
				</c:when>
				<c:otherwise>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<!-- Variable declarations for passing labels as parameters -->
								<!-- Table header for Computer Name -->

								<th class="editMode" style="width: 60px; height: 22px;"><input
									type="checkbox" id="selectall" /> <span
									style="vertical-align: top;"> - <a href="#"
										id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
											class="fa fa-trash-o fa-lg"></i>
									</a>
								</span></th>
								<th><spring:message code="dashboard.computer_name" /> <a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=0&order=true"><i
										class="fa fa-caret-down"></i></a><a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=0"><i
										class="fa fa-caret-up"></i></a></th>
								<th><spring:message code="dashboard.introduced_date" /> <a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=1&order=true"><i
										class="fa fa-caret-down"></i></a><a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=1"><i
										class="fa fa-caret-up"></i></a></th>
								<!-- Table header for Discontinued Date -->
								<th><spring:message code="dashboard.discontinued_date" /> <a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=2&order=true"><i
										class="fa fa-caret-down"></i></a><a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=2"><i
										class="fa fa-caret-up"></i></a></th>
								<!-- Table header for Company -->
								<th><spring:message code="dashboard.company" /> <a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=3&order=true"><i
										class="fa fa-caret-down"></i></a><a
									href="${ctx}/?page=${currentPage}&search=${search}&toOrder=3"><i
										class="fa fa-caret-up"></i></a></th>

							</tr>
						</thead>
						<!-- Browse attribute computers -->
						<tbody id="results">
							<c:forEach var="computer" items="${computers}">
								<tr>
									<td class="editMode"><input type="checkbox" name="cb"
										class="cb" value="${computer.id}"></td>
									<td><a
										href="${ctx}/editComputer/?computerId=${computer.id}"
										onclick=""> ${computer.name} </a></td>
									<td>${computer.introduced}</td>
									<td>${computer.discontinued}</td>
									<td>${computer.companyName}</td>
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
				<c:if test = "${ currentPage != 1}"> 
					<li><a
						href="${ctx}?page=${ currentPage - 1 > 0 ? currentPage -1: 1}&search=${search}&toOrder=${toOrder}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:forEach var="i" items="${pages}">

					<c:choose>
						<c:when test="${i == currentPage}">
							<li class="active"><a
								href="${ctx}?page=${i}&search=${search}&toOrder=${toOrder}">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="${ctx}?page=${i}&search=${search}&toOrder=${toOrder}">${i}</a></li>
						</c:otherwise>
					</c:choose>

				</c:forEach>
				<c:if test = "${ currentPage + 1 < totalPages}">
					<li><a href="${ctx}?page=${ currentPage + 1 }&search=${search}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if> 
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a class="btn btn-default" href="${ctx}?page=${ currentPage }&search=${search}&toOrder=${toOrder}&perPage=IDX_10">10</a>
				<a class="btn btn-default" href="${ctx}?page=${ currentPage }&search=${search}&toOrder=${toOrder}&perPage=IDX_50">50</a>
				<a class="btn btn-default" href="${ctx}?page=${ currentPage }&search=${search}&toOrder=${toOrder}&perPage=IDX_100">100</a>
			</div>
		</div>
	</footer>

	<%@include file="/views/imports/js.jsp"%>
</body>
</html>