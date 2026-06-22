package Catalog.application.ports.in;


public interface ICopyStatusEventListener {

	/**
	 * 
	 * @param copyId
	 */
	void handleCopyBorrowed(Integer copyId);

	/**
	 * 
	 * @param copyId
	 */
	void handleCopyReturned(Integer copyId);

	/**
	 * 
	 * @param copyId
	 */
	void handleCopyOverdue(Integer copyId);

}