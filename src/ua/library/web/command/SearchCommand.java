package ua.library.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.library.Path;
import ua.library.dao.DBManager;
import ua.library.dao.entity.Book;
import ua.library.exceptions.AppException;
import ua.library.web.enums.SidebarCategorie;

public class SearchCommand extends Command {

	private static final long serialVersionUID = -7633368781728044670L;
	private static final Logger LOG = Logger.getLogger(SearchCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		HttpSession session = request.getSession();
		String param = request.getParameter("param");
		Integer option = null;
		List<Book> books = null;
		LOG.debug("Command starts");

		if (request.getParameterMap().containsKey("option")) {
			option = Integer.parseInt(request.getParameter("option"));
		}
		LOG.trace("Get parameter :  option--> " + option);
		LOG.trace("Get parameter :  param--> " + param);
		LOG.trace("Switch :  " + SidebarCategorie.getCategorie(option));
		if (param != null && option != null) {

			switch (SidebarCategorie.getCategorie(option)) {
			case ALL:
				books = DBManager.getInstance().findBooksByName(param);
				LOG.trace("Found in DB:  books by NAME--> " + books);

				session.setAttribute("books", books);
				LOG.trace("Set the request attribute: books --> " + books);
				break;

			case AUTHORS:
				books = DBManager.getInstance().findBooksByAuthorName(param);
				LOG.trace("Found in DB:  books by AUTHORS--> " + books);

				session.setAttribute("books", books);
				LOG.trace("Set the session attribute: books --> " + books);
				break;

			case PUBLISHERS:
				books = DBManager.getInstance().findBooksByPublisherName(param);
				LOG.trace("Found in DB:  books by PUBLISHERS--> " + books);

				session.setAttribute("books", books);
				LOG.trace("Set the session attribute: books --> " + books);
				break;

			case PUBLICATION_DATE:

				break;
			default:
				break;
			}

		} else {

		}
		LOG.debug("Command finished");
		return Path.PAGE_SEARCH;
	}

}
