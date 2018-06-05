<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page pageEncoding="UTF-8"%>
<c:set var="categori"
	value="${pageContext.request.contextPath }${Path.COMMAND_MAIN_ADMIN_MENU }&categori" />

<%@include file="/WEB-INF/jspf/header.jspf"%>

<!-- content-wrapper-->
<div id="content-wrapper" class="container">
	<!-- /sidebar-->

	<div id="sidebar" class="container">



		<ul class="sidebar-links">
			<li><a href="${categori }=readers">Читатели</a></li>
			<li><a href="${categori }=librarians">Библиотекари</a></li>
			<li><a href="${categori }=books">Книги</a></li>
		</ul>
	</div>

	<!-- content-->
	<div id="content">
		<c:if test="${not empty param.categori}">

			<c:choose>
				<c:when
					test="${(param.categori eq 'readers') || (param.categori eq 'librarians')}">
					<%@include file="/WEB-INF/jspf/adminMain/componentUsers.jspf"%>
				</c:when>
				<c:otherwise>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="createBook" /> <input
							type="submit" value="добавить книгу">
					</form>
					<%@include file="/WEB-INF/jspf/adminMain/componentBooks.jspf"%>
				</c:otherwise>
			</c:choose>


		</c:if>

	</div>
	<!-- /content-->

</div>
<!-- /content-wrapper-->


<%@include file="/WEB-INF/jspf/footer.jspf"%>