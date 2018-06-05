package ua.library.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.library.Path;
import ua.library.dao.DBManager;
import ua.library.dao.been.UserLoansBeen;
import ua.library.dao.entity.User;
import ua.library.exceptions.AppException;
import ua.library.web.enums.SettingsCategories;

/**
 * View settings command.
 * 
 */
public class ViewSettingsCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(ViewSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		LOG.debug("Command starts");
		Integer pageId;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (request.getParameterMap().containsKey("page")) {
			pageId = Integer.parseInt(request.getParameter("page"));
		} else {
			pageId = 0;
		}

		switch (SettingsCategories.getCategorie(pageId)) {
		case MY_PROFILE:
			LOG.trace("Use session atribute: user --> " + user);
			break;
		case MY_BOOKS:
			List<UserLoansBeen> loans = DBManager.getInstance().findLoansbyUserId(user.getId());
			LOG.trace("Found in DB: all UserLoansBeen by user_id = " + user.getId() + " --> " + loans);
			session.setAttribute("userloans", loans);
			LOG.trace("Set the session attribute: userloans --> " + loans);
			break;

		case MESSAGES:

			break;

		case PROFILE_SETTINGS:

			break;

		default:
			break;
		}

		LOG.debug("Command finished");
		return Path.PAGE_SETTINGS;
	}

}