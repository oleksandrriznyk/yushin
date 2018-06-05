<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:forEach items="${sessionScope.books}" var="book">
	<!-- book-info-->
	<div class="book-info">
		<div class="book-title">
			<p>${book.getName() }</p>
		</div>
		<div class="book-img">
			<img src="${book.getPicturePath()}" height="250" width="190"
				alt="обложка" />
		</div>
		<div class="book-details">
			<br> <strong>ISBN:</strong> ${book.getIsbn()} <br> <strong>Издательство:</strong>
			${book.getPublishers()} <br> <strong>Количество
				страниц:</strong> ${book.getPages()} <br> <strong>Год издания:</strong>
			${book.getPublicationDate()}

			<p style="margin: 10px;">
				<a href="controller?read=book?book_id=ssadsa">Читать</a>
			</p>
			
		</div>

		

		
	</div>


	<!-- /book-info-->
</c:forEach>

</body>
</html>