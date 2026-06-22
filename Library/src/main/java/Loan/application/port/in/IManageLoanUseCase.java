package Loan.application.port.in;

public interface IManageLoanUseCase {

	/**
	 * 
	 * @param copyId
	 * @param clientId
	 */
	void loanCopy(Integer copyId, Integer clientId);

	/**
	 * 
	 * @param loanId
	 */
	void returnCopy(Integer loanId);

}