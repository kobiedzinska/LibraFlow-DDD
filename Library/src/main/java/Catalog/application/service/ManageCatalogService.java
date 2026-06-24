package Catalog.application.service;

import Catalog.application.ports.in.*;
import Catalog.application.ports.out.*;
import Catalog.application.domain.model.*;

public class ManageCatalogService implements ICatalogManagementUseCase {

	private IBookRepository bookRepository;
	private ICopyRepository copyRepository;

	public ManageCatalogService(IBookRepository bookRepository, ICopyRepository copyRepository) {
		this.bookRepository = bookRepository;
		this.copyRepository = copyRepository;
	}

	/**
	 * 
	 * @param c
	 */
	public void addCopy(Copy c) {
		Copy copy = new Copy(
				-1,
				c.getBookId(),
				CopyStatus.AVAILABLE
		);

		copyRepository.saveCopy(copy);
	}

	/**
	 * 
	 * @param b
	 */
	public void addBook(Book b) {
		bookRepository.saveBook(b);
	}

}