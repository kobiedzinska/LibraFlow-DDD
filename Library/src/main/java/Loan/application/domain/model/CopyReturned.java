package Loan.application.domain.model;

import java.time.LocalDateTime;

public class CopyReturned extends CopyEvent {
	public CopyReturned(int copyId, LocalDateTime occurredOn){
		super(copyId, occurredOn);
	}
}
