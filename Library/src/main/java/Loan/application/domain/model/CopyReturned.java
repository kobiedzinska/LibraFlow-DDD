package Loan.application.domain.model;

import java.time.LocalDateTime;

public class CopyReturned {

	private int copyId;
	private LocalDateTime occuredOn;

	public CopyReturned() {
		// TODO - implement CopyReturned.CopyReturned
		throw new UnsupportedOperationException();
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public LocalDateTime getOccuredOn() {
		return occuredOn;
	}

	public void setOccuredOn(LocalDateTime occuredOn) {
		this.occuredOn = occuredOn;
	}
}