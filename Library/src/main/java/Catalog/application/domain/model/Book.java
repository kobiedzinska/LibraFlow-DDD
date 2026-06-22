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
}