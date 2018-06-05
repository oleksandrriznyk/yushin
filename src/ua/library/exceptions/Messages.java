package ua.library.exceptions;

/**
 * Holder for messages of exceptions.
 * 
 *
 */
public class Messages {

	private Messages() {
		// no op
	}

	public static final String ERR_CANNOT_OBTAIN_CATALOGS = "Cannot obtain catalogs";
	public static final String ERR_CANNOT_OBTAIN_CATALOG_BY_ID = "Cannot obtain catalog by id";
	public static final String ERR_CANNOT_OBTAIN_CATALOG_BY_NAME = "Cannot obtain catalog by name";

	public static final String ERR_CANNOT_OBTAIN_AUTHORS_BY_NAME = "Cannot obtain authors by name";
	public static final String ERR_CANNOT_OBTAIN_AUTHOR_BY_ID = "Cannot obtain author by id";
	public static final String ERR_CANNOT_OBTAIN_AUTHORS = "Cannot obtain all authors";

	public static final String ERR_CANNOT_OBTAIN_PUBLISHERS = "Cannot obtain publishers";

	public static final String ERR_CANNOT_OBTAIN_USERS = "Cannot obtain users";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain user by login";
	public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user";
	public static final String ERR_CANNOT_INSERT_NEW_USER = "Cannot insert new user";

	public static final String ERR_CANNOT_OBTAIN_BOOKS = "Cannot obtain books";
	public static final String ERR_CANNOT_OBTAIN_BOOK_BY_ISBN = "Cannot obtain book by isbn";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLISHER_NAME = "Cannot obtain book by publisher name";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLISHER_ID = "Cannot obtain book by publisher id";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_GENRE_ID = "Cannot obtain book by genre id";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_AUTHOR_ID = "Cannot obtain book by author id";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_AUTHOR_NAME = "Cannot obtain book by author name";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLICATION_DATE = "Cannot obtain book by date published";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_NAME = "Cannot obtain book by name";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_CATALOG_NAME = "Cannot obtain book by catalog name";
	public static final String ERR_CANNOT_OBTAIN_BOOKS_BY_CATALOG_ID = "Cannot obtain book by catalog id";
	public static final String ERR_CANNOT_INSERT_NEW_BOOK = "Cannot insert new book";

	public static final String ERR_CANNOT_OBTAIN_LOANS_BY_USER_ID = "Cannot obtain userloans been";
	public static final String ERR_CANNOT_CHECK_FOR_FINE = "Cannot check user loans ";

	public static final String ERR_CANNOT_OBTAIN_GENRES = "Cannot obtain genre";

	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";
}