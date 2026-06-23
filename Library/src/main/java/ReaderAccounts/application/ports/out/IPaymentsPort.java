package ReaderAccounts.application.ports.out;

public interface IPaymentsPort {

	/**
	 * 
	 * @param readerId
	 */
	boolean hasPendingPayments(int readerId);

}