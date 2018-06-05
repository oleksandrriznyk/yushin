package ua.library.dao.been;

import java.sql.Date;

import ua.library.dao.entity.Entity;

public class UserLoansBeen extends Entity {

	private static final long serialVersionUID = -3349693569383169974L;

	private int bookIsbn;

	private String bookName;

	private Date aquireDate;

	private Date dueDate;

	private int fine;

	@Override
	public String toString() {
		return "UserLoansBeen [bookISBN=" + bookIsbn + ", bookName=" + bookName + ", aquireDate=" + aquireDate
				+ ", dueDate=" + dueDate + ", fine=" + fine + "]";
	}

	public int getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(int bookISBN) {
		this.bookIsbn = bookISBN;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Date getAquireDate() {
		return aquireDate;
	}

	public void setAquireDate(Date aquireDate) {
		this.aquireDate = aquireDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}
}
