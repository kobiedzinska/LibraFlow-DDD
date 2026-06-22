package Loan.application.port.in;

public interface IManageLoanUseCase {

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