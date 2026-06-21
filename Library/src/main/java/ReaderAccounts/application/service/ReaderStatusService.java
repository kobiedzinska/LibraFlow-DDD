package ReaderAccounts.application.service;

import ReaderAccounts.application.ports.in.*;
import ReaderAccounts.application.ports.out.*;

public class ReaderStatusService implements IReaderStatusEventListener {

	IUserRepository userRepozytorium;
	CopyPort copyPort;
	PaymentsPort paymentsPort;

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