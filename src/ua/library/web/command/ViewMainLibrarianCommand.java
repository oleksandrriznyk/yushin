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
import ua.library.dao.been.UserLoansBeen;
import ua.library.dao.entity.User;
import ua.library.exceptions.AppException;

public class ViewMainLibrarianCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5891784779855022166L;
	private static final Logger LOG = Logger.getLogger(ViewMainLibrarianCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		HttpSession session = request.getSession();
		LOG.debug("Command starts");
		List<User> users = (List<User>) session.getAttribute("usersList");
		if (users == null) {
			users = DBManager.getInstance().findUsers();
			session.setAttribute("usersList", users);
			LOG.trace("Get the session attribute: usersList --> " + users);
		}
		if (request.getParameterMap().containsKey("userId")) {
			Integer userId = Integer.parseInt(request.getParameter("userId"));
			LOG.trace("Get the request parameter: userId --> " + userId);

			List<UserLoansBeen> been = DBManager.getInstance().findLoansbyUserId(userId);
			session.setAttribute("userloans", been);
			LOG.trace("Get the session attribute: UserLoansBeen --> " + been);
		}

		LOG.debug("Command finished");

		return Path.PAGE_MAIN_LIBRARIAN;
	}

}