package Catalog.application.ports.out;

import Catalog.application.domain.model.*;

public interface ICopyRepository {

	/**
	 * 
	 * @param c
	 */
	void saveCopy(Copy c);

	/**
	 * 
	 * @param cId
	 */
	Copy findCopy(int cId);

}