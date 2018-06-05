package ua.library.dao.entity;

import java.sql.Date;

public class Loans extends Entity {

	private static final long serialVersionUID = -6838488462552477323L;

	@Override
	public String toString() {
		return "Loans [usersId=" + usersId + ", booksIsbn=" + booksIsbn + ", fine=" + fine + ", aquireDate="
				+ aquireDate + ", dueDate=" + dueDate + "]";
	}

	private int usersId;

	private int booksIsbn;

	private int fine;

	private Date aquireDate;

	private Date dueDate;

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	public int getBooksIsbn() {
		return booksIsbn;
	}

	public void setBooksIsbn(int booksIsbn) {
		this.booksIsbn = booksIsbn;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
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

}
