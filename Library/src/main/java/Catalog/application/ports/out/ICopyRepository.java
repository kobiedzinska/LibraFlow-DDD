package Catalog.application.ports.out;

import Catalog.application.domain.model.*;

import java.util.List;

public interface ICopyRepository {

	/**
	 * 
	 * @param c
	 */
	void saveCopy(Copy c);

	public List<Copy> findByBookId(int bookId);

	/**
	 * 
	 * @param cId
	 */
	Copy findCopy(int cId);

}