package ReaderAccounts.application.service;

import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyReturned;
import Payments.application.domain.model.PaymentCompleted;
import Payments.application.domain.model.PaymentOverdue;
import ReaderAccounts.application.ports.in.*;
import ReaderAccounts.application.ports.out.*;

public class ReaderStatusService implements IReaderStatusEventListener {

	IUserRepository userRepozytorium;
	ICopyPort copyPort;
	IPaymentsPort paymentsPort;

	/**
	 * 
	 * @param event
	 */
	public void handlePaymentOverdue(PaymentOverdue event) {
		// TODO - implement ReaderStatusService.handlePaymentOverdue
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param event
	 */
	public void handleCopyBorrowed(CopyBorrowed event) {
		// TODO - implement ReaderStatusService.handleCopyBorrowed
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param event
	 */
	public void handlePaymentCompleted(PaymentCompleted event) {
		// TODO - implement ReaderStatusService.handlePaymentCompleted
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param event
	 */
	public void handleCopyReturned(CopyReturned event) {
		// TODO - implement ReaderStatusService.handleCopyReturned
		throw new UnsupportedOperationException();
	}

}