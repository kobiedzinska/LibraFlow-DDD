package Loan.application.domain.model;

public class ReaderSnapshot {

	private Integer readerId;
	private Boolean isBlocked;
	private int activeLoansCount;

	public Integer getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public Boolean getBlocked() {
		return isBlocked;
	}

	public void setBlocked(Boolean blocked) {
		isBlocked = blocked;
	}

	public int getActiveLoansCount() {
		return activeLoansCount;
	}

	public void setActiveLoansCount(int activeLoansCount) {
		this.activeLoansCount = activeLoansCount;
	}
}