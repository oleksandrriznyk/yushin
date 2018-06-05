package ua.library.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.library.Path;
import ua.library.dao.DBManager;
import ua.library.dao.entity.User;
import ua.library.exceptions.AppException;
import ua.library.security.Password;

public class CreateUserCommand extends Command {

	private static final long serialVersionUID = -1542338423295396752L;
	private static final Logger LOG = Logger.getLogger(ViewCreateUserCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		DBManager manager = DBManager.getInstance();

		String login = request.getParameter("login");
		LOG.trace("Request parameter: loging --> " + login);

		String password = request.getParameter("password");
		LOG.trace("Request parameter: password --> " + password);

		String email = request.getParameter("email");
		LOG.trace("Request parameter: email --> " + email);

		String passwordConfirm = request.getParameter("passwordConfirm");
		LOG.trace("Request parameter: passwordConfirm --> " + passwordConfirm);

		String firstName = request.getParameter("firstName");
		LOG.trace("Request parameter: firstName --> " + firstName);

		String lastName = request.getParameter("lastName");
		LOG.trace("Request parameter: lastName --> " + lastName);

		String gender = request.getParameter("gender");
		LOG.trace("Request parameter: gender --> " + gender);
		Boolean isMalel = null;
		if ("male".equals(gender)) {
			isMalel = true;
		} else if ("female".equals(gender)) {
			isMalel = false;
		}

		User user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		checkForEmpty(login, password, email, passwordConfirm, firstName, lastName, isMalel);

		User userBeen = initUser(login, password, email, firstName, lastName, isMalel);
		LOG.trace("Init new User : user --> " + userBeen);
		manager.insertNewUser(userBeen);

		LOG.debug("Command finished");
		return Path.PAGE_MAIN;
	}

	private User initUser(String login, String password, String email, String firstName, String lastName,
			Boolean isMalel) {

		User user = new User();
		user.setLogin(login);
		user.setPasswordHash(Password.hashPassword(password));
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRoleId(3);
		user.setMale(isMalel);
		user.setPicturePath("/Library/images/users/user.png");

		return user;
	}

	private void checkForEmpty(String login, String pass, String email, String passwordConfirm, String firstName,
			String lastName, Boolean isMale) throws AppException {
		if (login == null || login.isEmpty()) {
			throw new AppException("login cannot be empty");
		}
		if (pass == null || pass.isEmpty()) {
			throw new AppException("password cannot be empty");
		}
		if (email == null || email.isEmpty()) {
			throw new AppException("email cannot be empty");
		}
		if (passwordConfirm == null || passwordConfirm.isEmpty()) {
			throw new AppException("passwordConfirm cannot be empty");
		}
		if (!pass.equals(passwordConfirm)) {
			throw new AppException("passwordConfirm and password must by equals");
		}
		if (firstName == null || firstName.isEmpty()) {
			throw new AppException("firstName cannot be empty");
		}
		if (lastName == null || lastName.isEmpty()) {
			throw new AppException("firstName cannot be empty");
		}
		if (isMale == null) {
			throw new AppException("isMale cannot be empty");
		}

	}
}