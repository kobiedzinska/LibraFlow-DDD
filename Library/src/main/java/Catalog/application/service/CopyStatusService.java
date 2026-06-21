package Catalog.application.service;

import Catalog.application.ports.in.*;
import Catalog.application.ports.out.*;
import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;


public class CopyStatusService implements ICopyStatusEventListener {

	private ICopyRepository copyRepository;

	/**
	 * 
	 * @param event
	 */
	public void handleCopyBorrowed(CopyBorrowed event) {
		// TODO - implement CopyStatusService.handleCopyBorrowed
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param event
	 */
	public void handleCopyReturned(CopyReturned event) {
		// TODO - implement CopyStatusService.handleCopyReturned
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param event
	 */
	public void handleCopyOverdue(CopyOverdue event) {
		// TODO - implement CopyStatusService.handleCopyOverdue
		throw new UnsupportedOperationException();
	}

}