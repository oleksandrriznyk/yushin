package ua.library.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.library.Path;
import ua.library.dao.DBManager;
import ua.library.dao.entity.Role;
import ua.library.dao.entity.User;
import ua.library.exceptions.AppException;
import ua.library.security.Password;

/**
 * Login command.
 * 
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		// obtain login and password from a request
		DBManager manager = DBManager.getInstance();
		String login = request.getParameter("login");

		LOG.trace("Request parameter: loging --> " + login);

		String password = request.getParameter("password");

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			throw new AppException("Login/password cannot be empty");
		}
		User user = manager.findUserByLogin(login);
		manager.checkLoansbyUserId(user.getId());
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !Password.checkPassword(password, user.getPasswordHash())) {
			throw new AppException("Cannot find user with such login/password");
		}
		Role userRole = Role.getRole(user);

		LOG.trace("userRole --> " + userRole);

		String forward = Path.PAGE_ERROR_PAGE;

		if (userRole == Role.ADMIN) {
			forward = Path.COMMAND_MAIN_ADMIN_MENU;
		}
		if (userRole == Role.LIBRARIAN) {
			forward = Path.COMMAND_MAIN_LIBRARIAN_MENU;
		}
		if (userRole == Role.READER) {
			forward = Path.COMMAND_MAIN_MENU;
		}

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;
	}

}