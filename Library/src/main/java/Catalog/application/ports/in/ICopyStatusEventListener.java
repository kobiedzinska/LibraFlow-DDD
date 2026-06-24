package Catalog.application.ports.in;


public interface ICopyStatusEventListener {

	/**
	 * 
	 * @param copyId
	 */
	void handleCopyBorrowed(int copyId);

	/**
	 * 
	 * @param copyId
	 */
	void handleCopyReturned(int copyId);

	/**
	 * 
	 * @param copyId
	 */
	void handleCopyOverdue(int copyId);

}