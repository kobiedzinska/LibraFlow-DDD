package Catalog.application.ports.out;

import Catalog.application.domain.model.*;

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

}