package Catalog.application.service;

import Catalog.application.ports.in.*;
import Catalog.application.ports.out.*;
import Catalog.application.domain.model.*;

public class ManageCatalogService implements ICatalogManagementUseCase {

	private IBookRepository bookRepository;
	private ICopyRepository copyRepository;

	/**
	 * 
	 * @param c
	 */
	public void addCopy(Copy c) {
		// TODO - implement ManageCatalogService.addCopy
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param b
	 */
	public void addBook(Book b) {
		// TODO - implement ManageCatalogService.addBook
		throw new UnsupportedOperationException();
	}

}