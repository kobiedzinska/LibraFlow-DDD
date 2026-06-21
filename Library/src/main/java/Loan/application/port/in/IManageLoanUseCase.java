package Loan.application.port.in;

public interface IManageLoanUseCase {

	/**
	 * 
	 * @param loanId
	 * @param clientId
	 */
	void loanCopy(int loanId, int clientId);

	/**
	 * 
	 * @param loanId
	 */
	void returnCopy(int loanId);

}