package Loan.infrastructure.in;

import Loan.application.port.in.*;

public class LoanController {

	private final IManageLoanUseCase loanService;

	public LoanController(IManageLoanUseCase loanService) {
		this.loanService = loanService;
	}

	public void loanCopy(Integer copyId, Integer readerId) {

		loanService.loanCopy(copyId, readerId);
	}

	public void returnLoan(Integer loanId) {
		loanService.returnLoan(loanId);
	}
}