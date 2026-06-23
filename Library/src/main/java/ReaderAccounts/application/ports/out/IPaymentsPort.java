package ReaderAccounts.application.ports.out;

public interface IPaymentsPort {

	/**
	 * 
	 * @param readerId
	 */
	int checkPayments(int readerId);

}