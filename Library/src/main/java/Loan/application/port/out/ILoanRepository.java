package Loan.application.port.out;

import Loan.application.domain.model.Loan;

import java.io.IOException;

public interface ILoanRepository {

	/**
	 * 
	 * @param loan
	 */
	void saveLoan(Loan loan);

	int countActiveLoansByReaderId(int loanId) throws IOException;

	/**
	 * 
	 * @param loanId
	 */
	Loan findLoan(int loanId);

}