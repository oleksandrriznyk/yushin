package ua.library.dao.entity;

import java.sql.Date;

public class Author extends Entity {

	private static final long serialVersionUID = 2301787527403266193L;

	private int id;

	private String name;

	private Date dateOfBirth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
