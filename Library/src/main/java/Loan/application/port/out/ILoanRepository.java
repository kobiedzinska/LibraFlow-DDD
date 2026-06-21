package Loan.application.port.out;

import Loan.application.domain.model.Loan;
public interface ILoanRepository {

	/**
	 * 
	 * @param loan
	 */
	void saveLoan(Loan loan);

	/**
	 * 
	 * @param loanId
	 */
	Loan findLoan(int loanId);

}