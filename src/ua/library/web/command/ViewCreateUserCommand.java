package ua.library.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.library.Path;
import ua.library.exceptions.AppException;

public class ViewCreateUserCommand extends Command {

	private static final long serialVersionUID = -2205323287742305968L;
	private static final Logger LOG = Logger.getLogger(ViewCreateUserCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		LOG.debug("Command finished");
		return Path.PAGE_CREATE_USER;
	}

}
