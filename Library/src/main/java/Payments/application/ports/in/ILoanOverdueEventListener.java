package Payments.application.ports.in;

public interface ILoanOverdueEventListener {

	/**
	 * 
	 * @param dueDate
	 * @param returnDate
	 */
	void handleLoanOverdue(int dueDate, int returnDate);

}