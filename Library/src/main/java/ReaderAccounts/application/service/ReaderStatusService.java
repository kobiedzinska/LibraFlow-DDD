package ReaderAccounts.application.service;

import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyReturned;
import Payments.application.domain.model.PaymentCompleted;
import Payments.application.domain.model.PaymentOverdue;
import ReaderAccounts.application.ports.in.*;
import ReaderAccounts.application.ports.out.*;

public class ReaderStatusService implements IReaderStatusEventListener {

	IUserRepository userRepozytorium;
	ILoanPort copyPort;
	IPaymentsPort paymentsPort;


	public void handlePaymentOverdue(int clientId) {
		// TODO - implement ReaderStatusService.handlePaymentOverdue
		throw new UnsupportedOperationException();
	}


	public void handlePaymentCompleted(int clientId) {
		// TODO - implement ReaderStatusService.handlePaymentCompleted
		throw new UnsupportedOperationException();
	}

}