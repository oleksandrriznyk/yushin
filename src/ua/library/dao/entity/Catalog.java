package ua.library.dao.entity;

import java.util.List;

public class Catalog extends Entity {

	private static final long serialVersionUID = -3891463799341672121L;

	private int id;

	private String name;

	private List<Book> books;

	@Override
	public String toString() {
		return "Catalog [id=" + id + ", name=" + name + ", books=" + books + "]";
	}

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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
