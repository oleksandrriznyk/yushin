package ua.library;

/**
 * Path holder (jsp pages, controller commands).
 * 
 */
public final class Path {

	// pages
	public static final String PAGE_LOGIN = "/index.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_MAIN = "/WEB-INF/jsp/client/main.jsp";
	public static final String PAGE_SEARCH = "/WEB-INF/jsp/client/search.jsp";
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/client/settings.jsp";
	public static final String PAGE_MAIN_ADMIN = "/WEB-INF/jsp/admin/main.jsp";
	public static final String PAGE_MAIN_LIBRARIAN = "/WEB-INF/jsp/librarian/main.jsp";
	public static final String PAGE_CREATE_USER = "/WEB-INF/jsp/createUser.jsp";

	// commands
	public static final String COMMAND_MAIN_MENU = "/controller?command=viewMain&categori=4";
	public static final String COMMAND_SETTINGS = "/controller?command=viewSettings";
	public static final String COMMAND_SHOW_BOOKS = "/controller?command=viewMain";

	public static final String COMMAND_VIEW_CREATER_USER = "/controller?command=viewCreateUser";;

	public static final String COMMAND_MAIN_ADMIN_MENU = "/controller?command=viewMainAdmin";
	public static final String COMMAND_MAIN_LIBRARIAN_MENU = "/controller?command=viewMainLibrarian";;
}