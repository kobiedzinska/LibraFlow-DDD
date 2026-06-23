package ReaderAccounts.application.ports.out;

public interface ILoanPort {

	/**
	 * 
	 * @param readerId
	 */
	boolean hasActiveLoans(int readerId);

}