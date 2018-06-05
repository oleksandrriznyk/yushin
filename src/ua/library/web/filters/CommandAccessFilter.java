package ua.library.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.library.Path;
import ua.library.dao.entity.Role;

/**
 * Security filter. Disabled by default. Uncomment Security filter section in
 * web.xml to enable.
 * 
 */
public class CommandAccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	// commands access
	private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private List<String> commons = new ArrayList<String>();
	private List<String> outOfControl = new ArrayList<String>();

	public void destroy() {
		LOG.debug("Filter destruction starts");
		// do nothing
		LOG.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Filter starts");

		if (accessAllowed(request)) {
			LOG.debug("Filter finished : Allowed");
			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
			String errorMessasge = "You do not have permission to access the requested resource";

//			request.setAttribute("errorMessage", errorMessasge);
			LOG.trace("Set the request attribute: errorMessage --> " + errorMessasge);

//			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			return true;
		}

		if (outOfControl.contains(commandName)) {
			return true;
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			return true;
		}

		Role userRole = (Role) session.getAttribute("userRole");
		if (userRole == null) {
			return true;
		}

		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");

		// roles
		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(Role.LIBRARIAN, asList(fConfig.getInitParameter("librarian")));
		accessMap.put(Role.READER, asList(fConfig.getInitParameter("reader")));
		LOG.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		LOG.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands --> " + outOfControl);

		LOG.debug("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

}