package ReaderAccounts.application.ports.out;

public interface PaymentsPort {

	/**
	 * 
	 * @param readerId
	 */
	int checkPayments(int readerId);

}