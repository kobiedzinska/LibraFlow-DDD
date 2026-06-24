package Loan.application.domain.model;

import java.time.LocalDateTime;

public class CopyOverdue extends CopyEvent {
	private final int readerId;

	public CopyOverdue(int copyId, int readerId, LocalDateTime occurredOn) {
		super(copyId, occurredOn);
		this.readerId=readerId;
	}

	public int getReaderId() {
		return readerId;
	}


}
