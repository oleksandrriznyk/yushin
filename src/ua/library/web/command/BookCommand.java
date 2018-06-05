package ua.library.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.library.dao.DBManager;
import ua.library.exceptions.AppException;

public class BookCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		DBManager manager = DBManager.getInstance();
		System.out.println(request.getParameter("book_id"));
		manager.bookBook(Integer.valueOf(request.getParameter("book_id")));
		return "booked_books.jsp";
	}

}
