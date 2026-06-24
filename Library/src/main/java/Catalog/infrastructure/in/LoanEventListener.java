package Catalog.infrastructure.in;

import Catalog.application.ports.in.*;


public class LoanEventListener {

	public ICopyStatusEventListener copyStatusService;

	public LoanEventListener(ICopyStatusEventListener copyStatusService) {
		this.copyStatusService = copyStatusService;
	}

	public void handleCopyBorrowed(Integer copyId) {
		copyStatusService.handleCopyBorrowed(copyId);
	}

	public void handleCopyReturned(Integer copyId) {
		copyStatusService.handleCopyReturned(copyId);
	}

	public void handleCopyOverdue(Integer copyId) {
		copyStatusService.handleCopyOverdue(copyId);
	}

}