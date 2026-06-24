package Loan.application.port.in;

public interface IManageLoanUseCase {

	/**
	 * 
	 * @param copyId
	 * @param readerId
	 */
	void loanCopy(int copyId, int readerId);

	/**
	 * 
	 * @param loanId
	 */
	void returnLoan(int loanId);

}