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
import ua.library.dao.entity.User;
import ua.library.exceptions.AppException;

public class ViewMainAdminCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4773848399250586236L;
	private static final Logger LOG = Logger.getLogger(ViewMainAdminCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		HttpSession session = request.getSession();
		LOG.debug("Command starts");
		String categori = request.getParameter("categori");
		LOG.trace("Get the request parameter: categori --> " + categori);

		if (categori != null && categori.equals("readers")) {
			List<User> users = DBManager.getInstance().findUsersByRoleID(3);
			request.setAttribute("users", users);
			LOG.trace("Set the request attribute: users(readers) --> " + users);

		} else if (categori != null && categori.equals("librarians")) {
			List<User> librarians = DBManager.getInstance().findUsersByRoleID(2);
			request.setAttribute("users", librarians);
			LOG.trace("Set the request attribute: users(librarians) --> " + librarians);

		} else if (categori != null && categori.equals("books")) {
			List<Book> books = DBManager.getInstance().findBooks();
			session.setAttribute("books", books);
			LOG.trace("Set the request attribute: books --> " + books);
		}
		LOG.debug("Command finished");

		return Path.PAGE_MAIN_ADMIN;
	}

}
