package Loan.application.domain.model;

import Loan.application.domain.model.*;

import java.time.LocalDateTime;

public class Loan {

	private int loanId;
	private int readerId;
	private int copyId;
	private LocalDateTime dueDate;
	private LocalDateTime borrowedAt;
	private LoanStatus loanStatus;
	private LocalDateTime returnedAt;

	/**
	 * 
	 * @param readerId
	 * @param copyId
	 * @param borrowedAt
	 */
	public Loan Loan(int readerId, int copyId, LocalDateTime borrowedAt) {
		// TODO - implement Loan.Loan
		throw new UnsupportedOperationException();
	}

	public void returnCopy() {
		// TODO - implement Loan.returnCopy
		throw new UnsupportedOperationException();
	}

	public Boolean isOverdue() {
		// TODO - implement Loan.isOverdue
		throw new UnsupportedOperationException();
	}

}