package ReaderAccounts.application.ports.out.http;

public interface ILoanPort {

	/**
	 * 
	 * @param readerId
	 */
	boolean hasActiveLoans(int readerId);

}