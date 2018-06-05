package ua.library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.library.dao.been.UserLoansBeen;
import ua.library.dao.entity.Author;
import ua.library.dao.entity.Book;
import ua.library.dao.entity.Catalog;
import ua.library.dao.entity.Genre;
import ua.library.dao.entity.Publisher;
import ua.library.dao.entity.User;
import ua.library.exceptions.DBException;
import ua.library.exceptions.Messages;

public class DBManager {
	private static DBManager instance;
	private DataSource ds;
	private static final Logger LOG = Logger.getLogger(DBManager.class);

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			// ST4DB - the name of data source
			ds = (DataSource) envContext.lookup("jdbc/root");
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	public void getAcces() {

	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////
	
	private static final String SQL_BOOK_BOOK = "INSERT INTO library.booked_books (book_id, book_date, book_period) values (?, ?, ?)";

	private static final String SQL_FIND_ALL_PUBLISHERS = "SELECT * FROM publishers";

	private static final String SQL_INSERT_NEW_USER = "INSERT INTO library.users (roles_id, login, password_hash, first_name, last_name, is_male, email,picture_path) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	private static final String SQL_FIND_USERS_BY_ROLES_ID = "SELECT * FROM users WHERE roles_id=?";
	private static final String SQL_UPDATE_USER = "UPDATE library.users SET  roles_id= ? , first_name=?, last_name=? , is_male=?, email=? WHERE id=?";

	private static final String SQL_FIND_ALL_BOOKS = "SELECT * FROM books";
	private static final String SQL_FIND_BOOK_BY_ISBN = "SELECT * FROM books WHERE isbn=?";
	private static final String SQL_FIND_BOOKS_BY_NAME = "SELECT * FROM books WHERE name LIKE ?";
	private static final String SQL_FIND_BOOKS_BY_PUBLICATION_DATE = "SELECT * FROM books WHERE publication_date BETWEEN ? AND ?";
	private static final String SQL_FIND_BOOKS_BY_PUBLISHER_NAME = "SELECT  books.* FROM books inner JOIN publishers ON books.publishers_id = publishers.id AND publishers.name LIKE ?";
	private static final String SQL_FIND_BOOKS_BY_PUBLISHER_ID = "SELECT  books.* FROM books inner JOIN publishers ON books.publishers_id = publishers.id AND publishers.id LIKE ?";
	private static final String SQL_FIND_BOOKS_BY_GENRE_ID = "SELECT  books.* FROM books inner JOIN genres ON books.genre_id  = genres.id AND genres.id LIKE ?";
	private static final String SQL_FIND_BOOKS_BY_AUTHOR_ID = "SELECT books.* FROM books INNER JOIN author_book ON books.isbn = author_book.books_isbn INNER JOIN authors ON author_book.authors_id =authors.id WHERE authors.id=?";
	private static final String SQL_FIND_BOOKS_BY_AUTHOR_NAME = "SELECT books.* FROM books INNER JOIN author_book ON books.isbn = author_book.books_isbn INNER JOIN authors ON author_book.authors_id=authors.id WHERE authors.name LIKE ? ";
	private static final String SQL_FIND_BOOKS_BY_CATALOG_NAME = "SELECT books.* FROM books INNER JOIN book_catalog ON books.isbn = book_catalog.books_isbn INNER JOIN catalogs ON book_catalog.catalogs_id =catalogs.id WHERE catalogs.name=?";
	private static final String SQL_FIND_BOOKS_BY_CATALOG_ID = "SELECT books.* FROM books INNER JOIN book_catalog ON books.isbn = book_catalog.books_isbn INNER JOIN catalogs ON book_catalog.catalogs_id =catalogs.id WHERE catalogs.id=?";
	private static final String SQL_INSERT_NEW_BOOK = "INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre`, `pages`, `description`, `picture_path`, `copies`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_FIND_AUTHORS_BY_NAME = "SELECT * FROM authors WHERE name=?";
	private static final String SQL_FIND_AUTHOR_BY_ID = "SELECT * FROM authors WHERE id=?";
	private static final String SQL_FIND_ALL_AUTHORS = "SELECT * FROM authors ORDER BY name";

	private static final String SQL_FIND_CATALOG_BY_ID = "SELECT * FROM catalogs WHERE id=?";
	private static final String SQL_FIND_CATALOG_BY_NAME = "SELECT * FROM catalogs WHERE name=?";
	private static final String SQL_FIND_ALL_CATALOGS = "SELECT * FROM catalogs ";

	private static final String SQL_FIND_LOANS_BY_USERS_ID = "SELECT books.isbn, books.name, loans.aquire_date , loans.due_date, loans.fine FROM books INNER JOIN loans ON books.isbn = loans.books_isbn INNER JOIN users ON loans.users_id =users.id WHERE users.id=?";
	private static final String CHECK_FOR_FINE = "UPDATE loans l  SET fine = DATEDIFF(l.due_date, curdate())    WHERE users_id = ? AND l.due_date < curdate()";

	private static final String SQL_FIND_ALL_GENRES = "SELECT * FROM genres";

	// //////////////////////////////////////////////////////////
	// Entity access methods
	// //////////////////////////////////////////////////////////

	/**
	 * Check fine by user id.
	 * 
	 */
	public void checkLoansbyUserId(int id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(CHECK_FOR_FINE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CHECK_FOR_FINE, ex);
			throw new DBException(Messages.ERR_CANNOT_CHECK_FOR_FINE, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Returns all loans by user id.
	 * 
	 * @return List<Loans> .
	 */
	public List<UserLoansBeen> findLoansbyUserId(int id) throws DBException {
		List<UserLoansBeen> loans = new ArrayList<UserLoansBeen>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_LOANS_BY_USERS_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				loans.add(extractUserLoansBeen(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LOANS_BY_USER_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LOANS_BY_USER_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return loans;
	}
	
	public boolean bookBook(int book_id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_BOOK_BOOK);
			pstmt.setInt(1, book_id);
			pstmt.setDate(2, java.sql.Date.valueOf("2018-09-04"));
			pstmt.setInt(3, 100);
			rs = pstmt.executeQuery();
			con.commit();
			return true;
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LOANS_BY_USER_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LOANS_BY_USER_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Returns all categories.
	 * 
	 * @return List of category entities.
	 */
	public List<Catalog> findCatalogs() throws DBException {
		List<Catalog> catalogList = new ArrayList<Catalog>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_CATALOGS);
			while (rs.next()) {
				catalogList.add(extractCatalog(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CATALOGS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATALOGS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return catalogList;
	}

	/**
	 * Returns all books.
	 * 
	 * @return List of books entities.
	 */
	public List<Book> findBooks() throws DBException {
		List<Book> books = new ArrayList<Book>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_BOOKS);
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return books;
	}

	/**
	 * Returns all categories.
	 * 
	 * @return List of category entities.
	 */
	public List<Author> findAuthors() throws DBException {
		List<Author> authorList = new ArrayList<Author>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_AUTHORS);
			while (rs.next()) {
				authorList.add(extractAuthor(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_AUTHORS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_AUTHORS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return authorList;
	}

	/**
	 * Returns all publisher.
	 * 
	 * @return List of publisher entities.
	 */
	public List<Publisher> findPublishers() throws DBException {
		List<Publisher> publisherList = new ArrayList<Publisher>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_PUBLISHERS);
			while (rs.next()) {
				publisherList.add(extractPublisher(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_PUBLISHERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_PUBLISHERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return publisherList;
	}

	/**
	 * Returns all genres.
	 * 
	 * @return List of genres entities.
	 */
	public List<Genre> findGenres() throws DBException {
		List<Genre> genres = new ArrayList<Genre>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_GENRES);
			while (rs.next()) {
				genres.add(extractGenre(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_GENRES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_GENRES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return genres;
	}

	/**
	 * Returns all users.
	 * 
	 * @return List of user entities.
	 */
	public List<User> findUsers() throws DBException {
		List<User> users = new ArrayList<User>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
			while (rs.next()) {
				users.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return users;
	}

	/**
	 * Returns user by login.
	 * 
	 * @return User .
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns user by role id.
	 * 
	 * @return List<User> .
	 */
	public List<User> findUsersByRoleID(int roleId) throws DBException {
		List<User> users = new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USERS_BY_ROLES_ID);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				users.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return users;
	}

	/**
	 * Returns book by isbn.
	 * 
	 * @return Book .
	 * @throws DBException
	 */
	public Book findBookByIsbn(int isbn) throws DBException {
		Book book = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOK_BY_ISBN);
			pstmt.setInt(1, isbn);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				book = extractBooks(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOK_BY_ISBN, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOK_BY_ISBN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return book;
	}

	/**
	 * Returns all books by name.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByName(String name) throws DBException {
		List<Book> books = new ArrayList<Book>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_NAME);
			pstmt.setString(1, "%" + name + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_NAME, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_NAME, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by date publication date.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByPublicationDate(Date date) throws DBException {
		List<Book> books = new ArrayList<Book>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_PUBLICATION_DATE);
			pstmt.setDate(1, date);
			pstmt.setDate(1, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLICATION_DATE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLICATION_DATE, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by publisher name.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByPublisherName(String publisher) throws DBException {
		List<Book> books = new ArrayList<Book>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_PUBLISHER_NAME);
			pstmt.setString(1, "%" + publisher + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLISHER_NAME, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLISHER_NAME, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by publisher id.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByPublisherId(int id) throws DBException {
		List<Book> books = new ArrayList<Book>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_PUBLISHER_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLISHER_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_PUBLISHER_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by publisher id.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByGenreId(int id) throws DBException {
		List<Book> books = new ArrayList<Book>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_GENRE_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_GENRE_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_GENRE_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by author id.
	 * 
	 * @return Book .
	 * @throws DBException
	 */
	public List<Book> findBooksByAuthorId(int id) throws DBException {
		List<Book> books = new ArrayList<Book>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_AUTHOR_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_AUTHOR_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_AUTHOR_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by catalog name.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByAuthorName(String name) throws DBException {
		List<Book> books = new ArrayList<Book>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_AUTHOR_NAME);
			pstmt.setString(1, "%" + name + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_AUTHOR_NAME, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_AUTHOR_NAME, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by catalog name.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByCatalogName(String name) throws DBException {
		List<Book> books = new ArrayList<Book>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_CATALOG_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_CATALOG_NAME, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_CATALOG_NAME, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Returns all books by catalog id.
	 * 
	 * @return List<Book> .
	 * @throws DBException
	 */
	public List<Book> findBooksByCatalogId(int id) throws DBException {
		List<Book> books = new ArrayList<Book>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BOOKS_BY_CATALOG_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				books.add(extractBooks(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_CATALOG_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOKS_BY_CATALOG_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return books;
	}

	/**
	 * Insert book.
	 * 
	 * @param book
	 *            user to insert.
	 * @throws DBException
	 */
	public void insertNewBook(Book book) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			insertBook(con, book);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_NEW_BOOK, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_NEW_BOOK, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Returns all authors by name.
	 * 
	 * @return List<Author> .
	 * @throws DBException
	 */
	public List<Author> findAuthorsByName(String name) throws DBException {
		List<Author> authors = new ArrayList<Author>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_AUTHORS_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				authors.add(extractAuthor(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_AUTHORS_BY_NAME, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_AUTHORS_BY_NAME, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return authors;
	}

	/**
	 * Returns author by id.
	 * 
	 * @return Author .
	 * @throws DBException
	 */
	public Author findAuthorById(int id) throws DBException {
		Author author = new Author();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_AUTHOR_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				author = extractAuthor(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_AUTHOR_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_AUTHOR_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return author;
	}

	/**
	 * Returns catalog by id.
	 * 
	 * @return Catalog .
	 * @throws DBException
	 */
	public Catalog findCatalogById(int id) throws DBException {
		Catalog catalog = new Catalog();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CATALOG_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				catalog = extractCatalog(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CATALOG_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATALOG_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return catalog;
	}

	/**
	 * Returns catalog by name.
	 * 
	 * @return Catalog .
	 * @throws DBException
	 */
	public Catalog findCatalogByName(String name) throws DBException {
		Catalog catalog = new Catalog();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CATALOG_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				catalog = extractCatalog(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CATALOG_BY_NAME, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATALOG_BY_NAME, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return catalog;
	}

	/**
	 * Insert user.
	 * 
	 * @param user
	 *            user to insert.
	 * @throws DBException
	 */
	public void insertNewUser(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			insertUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_NEW_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_NEW_USER, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws DBException
	 */
	public void updateUser(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	// //////////////////////////////////////////////////////////
	// Entity access methods (for transactions)
	// //////////////////////////////////////////////////////////

	/**
	 * Update user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws SQLException
	 */
	private void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			int k = 1;
			pstmt.setInt(k++, user.getRoleId());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, user.isMale() ? 1 : 0);
			pstmt.setString(k++, user.getEmail());

			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	// //////////////////////////////////////////////////////////
	// Other methods
	// //////////////////////////////////////////////////////////
	/**
	 * Insert user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws SQLException
	 */

	private void insertBook(Connection con, Book book) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_NEW_BOOK);
			int k = 1;
			pstmt.setInt(k++, book.getIsbn());
			pstmt.setString(k++, book.getName());
			pstmt.setDate(k++, book.getPublicationDate());
			pstmt.setInt(k++, book.getPublishers());
			pstmt.setInt(k++, book.getGenreId());
			pstmt.setInt(k++, book.getPages());
			pstmt.setString(k++, book.getDescriptions());
			pstmt.setString(k++, book.getPicturePath());
			pstmt.setInt(k++, book.getCopies());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	/**
	 * Insert user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws SQLException
	 */
	private void insertUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_NEW_USER);
			int k = 1;
			pstmt.setInt(k++, user.getRoleId());
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPasswordHash());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, user.isMale() ? 1 : 0);
			pstmt.setString(k++, user.getEmail());
			pstmt.setString(k++, user.getPicturePath());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user entity will be extracted.
	 * @return User entity
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setRoleId(rs.getInt("roles_id"));
		user.setLogin(rs.getString("login"));
		user.setPasswordHash(rs.getString("password_hash"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setMale(rs.getInt("is_male") == 1);
		user.setEmail(rs.getString("email"));
		user.setPicturePath(rs.getString("picture_path"));
		return user;

	}

	/**
	 * Extracts a books entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a books entity will be extracted.
	 * @return Book entity
	 * @throws SQLException
	 */
	private Book extractBooks(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setIsbn(rs.getInt("isbn"));
		book.setName(rs.getString("name"));
		book.setPublicationDate(rs.getDate("publication_date"));
		book.setPublishers(rs.getInt("publishers_id"));
		book.setGenreId(rs.getInt("genre_id"));
		book.setPages(rs.getInt("pages"));
		book.setDescriptions(rs.getString("description"));
		book.setPicturePath(rs.getString("picture_path"));
		book.setPages(rs.getInt("copies"));

		return book;

	}

	/**
	 * Extracts a genres entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a genre entity will be extracted.
	 * @return Genre entity
	 * @throws SQLException
	 */
	private Genre extractGenre(ResultSet rs) throws SQLException {
		Genre book = new Genre();
		book.setId(rs.getInt("id"));
		book.setName(rs.getString("name"));

		return book;

	}

	/**
	 * Extracts a publisher entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a publisher entity will be extracted.
	 * @return Publisher entity
	 * @throws SQLException
	 */
	private Publisher extractPublisher(ResultSet rs) throws SQLException {
		Publisher publisher = new Publisher();
		publisher.setId(rs.getInt("id"));
		publisher.setName(rs.getString("name"));
		publisher.setCity(rs.getString("city"));
		return publisher;

	}

	/**
	 * Extracts a author entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a author entity will be extracted.
	 * @return Author entity
	 * @throws SQLException
	 */
	private Author extractAuthor(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setId(rs.getInt("id"));
		author.setName(rs.getString("name"));
		author.setDateOfBirth(rs.getDate("date_of_birth"));
		return author;

	}

	/**
	 * Extracts a catalog entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a catalog entity will be extracted.
	 * @return Catalog entity
	 * @throws SQLException
	 */
	private Catalog extractCatalog(ResultSet rs) throws SQLException {
		Catalog catalog = new Catalog();
		catalog.setId(rs.getInt("id"));
		catalog.setName(rs.getString("name"));
		return catalog;

	}

	/**
	 * Extracts a loans entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a loans entity will be extracted.
	 * @return UserLoansBeen entity
	 * @throws SQLException
	 */
	private UserLoansBeen extractUserLoansBeen(ResultSet rs) throws SQLException {
		UserLoansBeen loans = new UserLoansBeen();
		loans.setBookIsbn(rs.getInt("isbn"));
		loans.setBookName(rs.getString("name"));
		loans.setAquireDate(rs.getDate("aquire_date"));
		loans.setDueDate(rs.getDate("due_date"));
		loans.setFine(rs.getInt("fine"));
		return loans;
	}
	// //////////////////////////////////////////////////////////
	// DB util methods
	// //////////////////////////////////////////////////////////

	public Connection getConnectionWithDriverManager() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException");
		}
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/library?useSSL=false", "root",
				"root");
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		connection.setAutoCommit(false);
		return connection;
	}

	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				System.out.println("SQLException");
			}
		}
	}

	public static void main(String[] args) throws DBException {
		DBManager dbManager = DBManager.getInstance();
		// System.out.println(dbManager.findAuthorsByName("Рей"));
	}
}
