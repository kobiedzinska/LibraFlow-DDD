package Catalog.application.ports.out;

import Catalog.application.domain.model.*;

import java.util.List;

public interface IBookRepository {

	/**
	 * 
	 * @param b
	 */
	void saveBook(Book b);

	/**
	 * 
	 * @param bId
	 */
	Book findBook(int bId);

	public List<Book> search(String query);

}