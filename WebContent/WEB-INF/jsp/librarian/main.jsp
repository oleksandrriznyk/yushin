<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page pageEncoding="UTF-8"%>
<c:set var="userId"
	value="${pageContext.request.contextPath }${Path.COMMAND_MAIN_LIBRARIAN_MENU }&userId=" />

<%@include file="/WEB-INF/jspf/header.jspf"%>

<!-- content-wrapper-->
<div id="content-wrapper" class="container">
	<!-- /sidebar-->

	<div id="sidebar" class="container">



		<ul class="sidebar-links">


			<c:forEach items="${sessionScope.usersList }" var="user">
				<li><a href="${userId }${user.getId()}">${user.getFirstName()}
						${user.getLastName()}</a></li>
			</c:forEach>


		</ul>
	</div>

	<!-- content-->
	<div id="content">
		<c:if test="${not empty sessionScope.userloans}">
			<table id="t01">

				<tr>
					<th>ISBN</th>
					<th>Название книги</th>
					<th>Дата выдачи</th>
					<th>Дата сдачи</th>
					<th>Пеня</th>
				</tr>
				<c:forEach items="${userloans}" var="loans">
					<tr>
						<td>${loans.getBookIsbn() }</td>
						<td>${loans.getBookName() }</td>
						<td>${loans.getAquireDate() }</td>
						<td>${loans.getDueDate() }</td>
						<td>${loans.getFine() }</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

	</div>
	<!-- /content-->

</div>
<!-- /content-wrapper-->


<%@include file="/WEB-INF/jspf/footer.jspf"%>