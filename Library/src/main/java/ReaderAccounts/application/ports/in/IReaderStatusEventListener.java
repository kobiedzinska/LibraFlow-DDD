package ReaderAccounts.application.ports.in;

public interface IReaderStatusEventListener {

	/**
	 * 
	 * @param event
	 */
	void handlePaymentOverdue(PaymentOverdue event);

	/**
	 * 
	 * @param event
	 */
	void handleCopyBorrowed(CopyBorrowed event);

	/**
	 * 
	 * @param event
	 */
	void handleCopyReturned(CopyReturned event);

	/**
	 * 
	 * @param event
	 */
	void handlePaymentCompleted(PaymentCompleted event);

}