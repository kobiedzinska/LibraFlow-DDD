package Catalog.application.ports.in;

import Catalog.application.domain.model.*;

public interface ICatalogManagementUseCase {

	/**
	 * 
	 * @param c
	 */
	void addCopy(Copy c);

	/**
	 * 
	 * @param b
	 */
	void addBook(Book b);

}