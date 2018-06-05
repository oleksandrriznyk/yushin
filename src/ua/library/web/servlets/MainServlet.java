package ua.library.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.library.dao.DBManager;
import ua.library.exceptions.DBException;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("sssss");
		System.out.println("sssss");
		DBManager dbManager;
		try {

			dbManager = DBManager.getInstance();
			// out.println(dbManager.findCatalogs());
			// out.println(dbManager.findAuthors());
			// out.println(dbManager.findPublishers());
			// out.println(dbManager.findUsers());
			// out.println(dbManager.findBooks());
			// out.println(dbManager.findUserByLogin("log1"));
			// out.println(dbManager.findBookByIsbn(26234745));
			// out.println(dbManager.findBooksByAuthorId(2));
			// out.println(dbManager.findBooksByPublisherName("Дао Toyota"));
			out.println(dbManager.findBooksByAuthorName("Рей Брэдбери"));
			out.println(dbManager.findBooksByPublisherName("Дао"));
			out.println(dbManager.findBooksByName("451"));
		} catch (DBException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
