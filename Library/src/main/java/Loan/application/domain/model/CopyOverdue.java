package Loan.application.domain.model;

import java.time.LocalDateTime;

public class CopyOverdue {

	private int copyId;
	private int readerId;
	private LocalDateTime occuredOn;

	public CopyOverdue() {
		// TODO - implement CopyOverdue.CopyOverdue
		throw new UnsupportedOperationException();
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public LocalDateTime getOccuredOn() {
		return occuredOn;
	}

	public void setOccuredOn(LocalDateTime occuredOn) {
		this.occuredOn = occuredOn;
	}
}