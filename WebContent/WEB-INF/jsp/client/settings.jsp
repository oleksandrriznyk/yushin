<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="ua.library.Path"%>
<c:set var="URL" value="${pageContext.request.requestURL }" />
<c:set var="URI" value="${pageContext.request.requestURI }" />
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<c:set var="settingsPage"
	value="${fn:replace(URL, URI, contextPath)}${Path.COMMAND_SETTINGS}&page=" />
<!DOCTYPE html>

<%@include file="/WEB-INF/jspf/header.jspf"%>


<!-- content-wrapper-->
<div id="content-wrapper" class="container">
	<!-- /sidebar-->
	<div id="sidebar" class="container">

		<div>
			<ul class="sidebar-links">
				<li><a href="${settingsPage}0">Мой профиль<a></li>
				<li><a href="${settingsPage}1">Мои книги<a></li>
				<li><a href="${settingsPage}2">Мои сообщения<a></li>
				<li><a href="${settingsPage}3">Настройки профиля<a></li>

			</ul>
		</div>
	</div>
	
	<c:choose>
		<c:when test="${param.page eq 0}">
			<%@include file="/WEB-INF/jspf/settings/contentUserProfile.jspf"%>
		</c:when>
		<c:when test="${param.page eq 1}">
			<%@include file="/WEB-INF/jspf/settings/contentUserBooks.jspf"%>
		</c:when>
		<c:when test="${param.page eq 2}">
			<%@include file="/WEB-INF/jspf/settings/contentUserMessages.jspf"%>
		</c:when>
		<c:when test="${param.page eq 3}">
			<%@include file="/WEB-INF/jspf/settings/contentUserSettings.jspf"%>
		</c:when>
		<c:otherwise>
			<%@include file="/WEB-INF/jspf/settings/contentUserProfile.jspf"%>
		</c:otherwise>
	</c:choose>
</div>

<%@include file="/WEB-INF/jspf/footer.jspf"%>