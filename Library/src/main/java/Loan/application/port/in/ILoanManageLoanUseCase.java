package Loan.application.port.in;

public interface ILoanManageLoanUseCase {

	/**
	 * 
	 * @param copyId
	 * @param readerId
	 */
	void loanCopy(Integer copyId, Integer readerId);

	/**
	 * 
	 * @param loanId
	 */
	void returnLoan(Integer loanId);

}