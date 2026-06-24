package Loan.application.domain.model;


import java.time.LocalDate;
import java.time.LocalDateTime;


public class Loan {

	private Integer loanId;
	private Integer readerId;
	private Integer copyId;
	private LocalDateTime dueDate;
	private LocalDateTime borrowedAt;
	private LoanStatus loanStatus;
	private LocalDateTime returnedAt;

	public Loan(Integer loanId, Integer readerId, Integer copyId,
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


	public CopyBorrowed borrowEvent() {
		return new CopyBorrowed(copyId, readerId, LocalDateTime.now());
	}

	public void returnCopy() {
		this.loanStatus = LoanStatus.RETURNED;
		this.returnedAt = LocalDateTime.now();
	}

	/*public boolean isOverdue() {
		return dueDate.isBefore(returnedAt);
	}*/
	public boolean isOverdue() {

		return dueDate.isBefore(returnedAt);
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public Integer getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDateTime getBorrowedAt() {
		return borrowedAt;
	}

	public void setBorrowedAt(LocalDateTime borrowedAt) {
		this.borrowedAt = borrowedAt;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	public LocalDateTime getReturnedAt() {
		return returnedAt;
	}

	public void setReturnedAt(LocalDateTime returnedAt) {
		this.returnedAt = returnedAt;
	}
}