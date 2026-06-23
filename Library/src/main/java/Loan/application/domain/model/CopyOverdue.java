package Loan.application.domain.model;

import java.time.LocalDateTime;

public class CopyOverdue extends CopyEvent {


	public CopyOverdue(int copyId, LocalDateTime occurredOn){
		super(copyId, occurredOn);
	}


}
