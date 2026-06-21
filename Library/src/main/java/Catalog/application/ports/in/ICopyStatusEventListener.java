package Catalog.application.ports.in;

import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;

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