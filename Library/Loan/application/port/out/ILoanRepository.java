package Loan.application.port.out;

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