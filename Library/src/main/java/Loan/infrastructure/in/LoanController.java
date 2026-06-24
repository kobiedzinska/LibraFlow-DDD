package Loan.infrastructure.in;

import Loan.application.port.in.*;

public class LoanController {

	private final IManageLoanUseCase loanService;

	public LoanController(IManageLoanUseCase loanService) {
		this.loanService = loanService;
	}

	public void loanCopy(int copyId, Integer readerId) {

		loanService.loanCopy(copyId, readerId);
	}

	public void returnLoan(int loanId) {
		loanService.returnLoan(loanId);
	}
}