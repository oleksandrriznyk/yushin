package ua.library.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.library.exceptions.AppException;

public class CreateBookCommand extends Command {

	private static final long serialVersionUID = -1407324542591552583L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		return null;
	}

}
