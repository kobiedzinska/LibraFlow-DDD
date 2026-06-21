package Catalog.application.ports.in;

public interface ICopyStatusEventListener {

	/**
	 * 
	 * @param event
	 */
	void handleCopyBorrowed(CopyBorrowed event);

	/**
	 * 
	 * @param event
	 */
	void handleCopyReturned(CopyReturned event);

	/**
	 * 
	 * @param event
	 */
	void handleCopyOverdue(CopyOverdue event);

}