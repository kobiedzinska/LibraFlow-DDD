package Catalog.infrastructure.in;

import Catalog.application.ports.in.*;


public class LoanEventListener {

	public ICopyStatusEventListener copyStatusService;

	public LoanEventListener(ICopyStatusEventListener copyStatusService) {
		this.copyStatusService = copyStatusService;
	}

	public void handleCopyBorrowed(int copyId) {
		copyStatusService.handleCopyBorrowed(copyId);
	}

	public void handleCopyReturned(int copyId) {
		copyStatusService.handleCopyReturned(copyId);
	}

	public void handleCopyOverdue(int copyId) {
		copyStatusService.handleCopyOverdue(copyId);
	}

}