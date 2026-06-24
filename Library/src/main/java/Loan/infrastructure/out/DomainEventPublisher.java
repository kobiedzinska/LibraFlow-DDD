package Loan.infrastructure.out;


import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;
import Loan.application.port.out.*;
import SharedKernel.DomainEvent;
import SharedKernel.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisher implements IDomainEventPublisher {
	private final EventBus eventBus;

	public DomainEventPublisher(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void publish(DomainEvent event) {
		eventBus.publish(event);
	}


}