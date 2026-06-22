/*
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

	public Loan(int loanId, int readerId, int copyId, LocalDateTime dueDate, LocalDateTime borrowedAt, LoanStatus loanStatus, LocalDateTime returnedAt) {
		this.loanId = loanId;
		this.readerId = readerId;
		this.copyId = copyId;
		this.dueDate = dueDate;
		this.borrowedAt = borrowedAt;
		this.loanStatus = loanStatus;
		this.returnedAt = returnedAt;
	}

	*/
/**
	 * 
	 * @param readerId
	 * @param copyId
	 * @param borrowedAt
	 *//*




	public Loan Loan(int readerId, int copyId, LocalDateTime borrowedAt) {
		// TODO - implement Loan.Loan
		throw new UnsupportedOperationException();
	}

	public void returnCopy() {
		loanStatus=LoanStatus.RETURNED;
		returnedAt=LocalDateTime.now();
	}

	public Boolean isOverdue() {
        return loanStatus == LoanStatus.OVERDUE;
	}

}*/

package Loan.application.domain.model;

import Loan.application.domain.events.CopyBorrowedEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Loan {

	private Integer loanId;
	private int readerId;
	private int copyId;
	private LocalDateTime dueDate;
	private LocalDateTime borrowedAt;
	private LoanStatus loanStatus;
	private LocalDateTime returnedAt;

	public Loan(Integer loanId, int readerId, int copyId,
				LocalDateTime dueDate,
				LocalDateTime borrowedAt,
				LoanStatus loanStatus,
				LocalDateTime returnedAt) {

		this.loanId = loanId;
		this.readerId = readerId;
		this.copyId = copyId;
		this.dueDate = dueDate;
		this.borrowedAt = borrowedAt;
		this.loanStatus = loanStatus;
		this.returnedAt = returnedAt;
	}

	public static Loan create(Integer loanId, int readerId, int copyId) {

		LocalDateTime now = LocalDateTime.now();

		return new Loan(
				loanId,
				readerId,
				copyId,
				now.plusDays(30),
				now,
				LoanStatus.BORROWED,
				null
		);
	}

	public CopyBorrowedEvent borrowEvent() {
		return new CopyBorrowedEvent(copyId, readerId, LocalDateTime.now());
	}

	public void returnCopy() {
		this.loanStatus = LoanStatus.RETURNED;
		this.returnedAt = LocalDateTime.now();
	}

	public boolean isOverdue() {
		return loanStatus == LoanStatus.OVERDUE;
	}
}