package Payments.application.ports.in;

import java.time.LocalDateTime;

public interface ILoanOverdueEventListener {

	/**
	 * 
	 * @param dueDate
	 * @param returnDate
	 */
	void handleLoanOverdue(LocalDateTime dueDate, LocalDateTime returnDate, int clientId, int loanId);

}