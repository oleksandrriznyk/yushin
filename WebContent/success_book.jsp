<%@page import="ua.library.dao.entity.Book"%>
<%@page import="ua.library.dao.been.LetterList"%>

<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Книга под номером <c:out value="${param.book_id}"></c:out> 
		с названием <c:out value="${param.book_name}"></c:out>
		была успешно забронирована</h1>
		<div align="center" class="book-img">
			<img src="${param.ques}" height="250" width="190"
				alt="обложка" />
		</div>
</body>
</html>