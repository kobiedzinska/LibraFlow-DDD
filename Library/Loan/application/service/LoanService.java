package Loan.application.service;

import Loan.application.port.in.*;
import Loan.application.port.out.*;
import Loan.application.domain.service.*;

public class LoanService implements IManageLoanUseCase {

	private ILoanRepository loanRepository;
	private IDomainEventPublisher eventPublisher;
	private LoanCopyPolicy loanPolicy;
	private IReaderRepository readerRepository;

	/**
	 * 
	 * @param loanId
	 * @param clientId
	 */
	public void loanCopy(int loanId, int clientId) {
		// TODO - implement LoanService.loanCopy
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param loanId
	 */
	public void returnCopy(int loanId) {
		// TODO - implement LoanService.returnCopy
		throw new UnsupportedOperationException();
	}

}