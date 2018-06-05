package ua.library.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.library.Path;
import ua.library.dao.DBManager;
import ua.library.dao.entity.Book;
import ua.library.dao.entity.Entity;
import ua.library.exceptions.AppException;
import ua.library.web.enums.SidebarCategorie;

public class ViewMainCommand extends Command {

	private static final long serialVersionUID = 4283088888772437859L;

	private static final Logger LOG = Logger.getLogger(ViewMainCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		HttpSession session = request.getSession();
		LOG.debug("Command starts");
		Map<String, List<? extends Entity>> links = (Map<String, List<? extends Entity>>) request.getServletContext()
				.getAttribute("sidebarLinks");
		if (links == null) {
			links = new HashMap<>();
			links.put(new String("0"), DBManager.getInstance().findCatalogs());
			links.put(new String("1"), DBManager.getInstance().findAuthors());
			links.put(new String("2"), DBManager.getInstance().findGenres());
			links.put(new String("3"), DBManager.getInstance().findPublishers());
			request.getServletContext().setAttribute("sidebarLinks", links);
			LOG.trace("Set the application attribute: sidebarLinks --> " + links);
		}

		List<Book> books = (List<Book>) session.getAttribute("books");
		if (books == null) {
			books = DBManager.getInstance().findBooks();
			session.setAttribute("books", books);
			LOG.trace("Set the session attribute: all books  --> " + links);
		}
		Integer categori;
		Integer id;
		String value = (String) request.getParameter("sortBy");
		if (request.getParameterMap().containsKey("id")) {
			id = Integer.parseInt(request.getParameter("id"));
		} else {
			id = null;
		}
		if (request.getParameterMap().containsKey("categori")) {
			categori = Integer.parseInt(request.getParameter("categori"));
		} else {
			categori = 4;
		}
		if (value != null) {
			sortBookByName(books, value, session);
		} else if (id != null || categori == 4) {

			switch (SidebarCategorie.getCategorie(categori)) {

			case СATALOGS:
				session.setAttribute("currentNamePage", DBManager.getInstance().findCatalogById(id).getName());
				books = DBManager.getInstance().findBooksByCatalogId(id);
				LOG.trace("Found in DB: all books by СATALOGS--> " + books);
				session.setAttribute("books", books);
				LOG.trace("Set the session attribute: books --> " + books);
				break;

			case AUTHORS:

				session.setAttribute("currentNamePage", DBManager.getInstance().findAuthorById(id).getName());
				books = DBManager.getInstance().findBooksByAuthorId(id);
				LOG.trace("Found in DB: all books by AUTHORS--> " + books);
				session.setAttribute("books", books);
				LOG.trace("Set the session attribute: books --> " + books);
				break;

			case GENRES:

				books = DBManager.getInstance().findBooksByGenreId(id);
				LOG.trace("Found in DB: all books by GENRES--> " + books);
				session.setAttribute("books", books);
				LOG.trace("Set the session attribute: books --> " + books);
				break;

			case PUBLISHERS:
				books = DBManager.getInstance().findBooksByPublisherId(id);
				LOG.trace("Found in DB: all books by PUBLISHERS--> " + books);
				session.setAttribute("books", books);
				LOG.trace("Set the session attribute: books --> " + books);
				break;

			case ALL:
				books = DBManager.getInstance().findBooks();
				LOG.trace("Found in DB: all books --> " + books);
				session.setAttribute("books", books);
				LOG.trace("Set the session attribute: books --> " + books);

			default:
				break;
			}
		}
		LOG.debug("Command finished");
		return Path.PAGE_MAIN;
	}

	private void sortBookByName(List<Book> books, String value, HttpSession session) {
		LOG.trace("Sort booksby value: --> " + value);
		List<Book> sortedResult = null;
		if (books != null && value != null) {
			sortedResult = new ArrayList<>();
			for (Book book : books) {
				if (book.getName().startsWith(value)) {
					sortedResult.add(book);
				}
			}
			session.setAttribute("books", sortedResult);
		}
	}

}