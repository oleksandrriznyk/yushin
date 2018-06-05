package ua.library.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ShowAtributes")
public class ShowAtributes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowAtributes() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession httpSession = request.getSession();
		List<Integer> list = (List<Integer>) httpSession.getAttribute("list");
		if (list.size() > 3) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			out.println(list);
		}
		out.println(list);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
