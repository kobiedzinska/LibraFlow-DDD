/*
package Catalog.infrastructure.in;

import Catalog.application.ports.in.*;
import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;
import SharedKernel.DomainEvent;
import SharedKernel.EventHandler;


public class LoanEventListener implements EventHandler<DomainEvent> {

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
*/
/*	@Override
	public void handle(DomainEvent event) {

		if (event instanceof CopyBorrowed e) {
			copyStatusService.handleCopyBorrowed(e.getCopyId());
		}

		if (event instanceof CopyReturned e) {
			copyStatusService.handleCopyReturned(e.getCopyId());
		}

		if (event instanceof CopyOverdue e) {
			copyStatusService.handleCopyOverdue(e.getCopyId());
		}
	}*//*


}*/
