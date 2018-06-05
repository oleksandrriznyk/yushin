package ua.library.dao.entity;

import java.sql.Date;

public class Book extends Entity {
	
	private int id;

	private static final long serialVersionUID = 1818147483741993355L;

	private int isbn;

	private String name;

	private Date publicationDate;

	private int publishers_id;

	private int genreId;

	private int pages;

	private String descriptions;

	private int copies;

	private String picturePath;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreIdd) {
		this.genreId = genreIdd;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", name=" + name + ", datePublished=" + publicationDate + ", publishers_id="
				+ publishers_id + ", genre_id=" + genreId + ", pages=" + pages + ", descriptions=" + descriptions
				+ ", copies=" + copies + ", picturePath=" + picturePath + "]/n";
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date datePublished) {
		this.publicationDate = datePublished;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPublishers() {
		return publishers_id;
	}

	public void setPublishers(int publishers_id) {
		this.publishers_id = publishers_id;
	}
}
