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
		copyRepository.saveCopy(c);
	}

	/**
	 * 
	 * @param b
	 */
	public void addBook(Book b) {
		Copy copy = new Copy(
				-1,
				b.getBookId(),
				CopyStatus.AVAILABLE
		);

		copyRepository.saveCopy(copy);

	}

}