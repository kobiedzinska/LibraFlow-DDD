package ReaderAccounts.application.ports.out.http;

public interface IPaymentsPort {

	/**
	 * 
	 * @param readerId
	 */
	boolean hasPendingPayments(int readerId);

}