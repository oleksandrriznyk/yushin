<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@include file="/WEB-INF/jspf/nls/localizedMessagesIndex.jspf"%>
<%@page import="ua.library.Path"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html >
<html>
<c:set var="title" value="Login" />
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/css/style-index.css"
	type="text/css" />
<title>Library</title>
</head>
<body>
	<div class="container">
		<img src="${pageContext.request.contextPath}/images/logo.png">

		<form action="controller" name="user" style="height: 198px;"
			method="post">
			<input type="hidden" name="command" value="login" />

			<div class="dws-input">
				<input type="text" name="login" placeholder="${username}">
			</div>

			<div class="dws-input">
				<input type="password" name="password" placeholder="${password}">
			</div>

			<div class="dws-input">
				<input class="dws-submit" type="submit" value="${buttonValue}">
			</div>
			<br> <a href="createUser.jsp">Регистрация</a> <br>


		</form>
		<form method="post">
			<select id="language" name="language" onchange="submit()">
				<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
				<option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
			</select>
		</form>
	</div>
</body>
</html>