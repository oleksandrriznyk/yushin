package ua.library.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.library.Path;
import ua.library.dao.entity.Book;
import ua.library.exceptions.AppException;

public class SortBooksByNameCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		HttpSession session = request.getSession();
		List<Book> books = (List<Book>) session.getAttribute("books");
		List<Book> sortedResult = null;
		String value = (String) session.getAttribute("sortBy");
		System.out.println(session.getAttribute("sortBy"));
		System.out.println(books);
		if (books != null && value != null) {
			sortedResult = new ArrayList<>();
			for (Book book : books) {
				if (book.getName().startsWith(value)) {
					sortedResult.add(book);
					System.out.println(book.getName());
				}
			}
			session.setAttribute("books", sortedResult);
		}

		return Path.PAGE_MAIN;
	}

}
