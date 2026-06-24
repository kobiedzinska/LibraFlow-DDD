package Catalog.application.domain.model;

import java.util.List;

public class Book {

	private Integer bookId;
	private String title;
	private String author;
	private String ISBN;
	private String publisher;
	private String publicationYear;
	private List<String> category;
	private String description;

	public Book(Integer bookId, String title, String author, String ISBN, String publisher, String publicationYear, List<String> category, String description) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
		this.category = category;
		this.description = description;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}