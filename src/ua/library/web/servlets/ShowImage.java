package ua.library.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ShowImage")
public class ShowImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int count = 0;

	public ShowImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Integer> list = null;
		PrintWriter out = null;
		Enumeration<String> enumeration = request.getParameterNames();
		count++;
		HttpSession session = request.getSession();
		session.setAttribute("count", count);
		try {
			out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>show!</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>show</h1>");
			out.println("<h1>session id = " + request.getSession(true).getId() + "</h1>");
			out.println("<h1>" + request.getSession().getAttribute("count") + "</h1>");
			while (enumeration.hasMoreElements()) {
				String string = (String) enumeration.nextElement();
				out.println("<h1>");
				out.println(string);
				out.println(request.getParameter(string));
				out.println(request.getContextPath());
				out.println("</h1>");
			}
			int num = Integer.parseInt(request.getParameter("sq"));
			if (session.isNew()) {
				list = new ArrayList<>();
			} else {
				list = (List<Integer>) session.getAttribute("list");
			}
			list.add(num);
			session.setAttribute("list", list);
			out.println("<table>");
			while (num-- > 0) {

				out.println("<tr>");
				out.println("<td>" + num + "</td>");
				out.println("<td>" + (num * num) + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("<img src = '" + request.getContextPath() + "/images/photo.jpg'>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
