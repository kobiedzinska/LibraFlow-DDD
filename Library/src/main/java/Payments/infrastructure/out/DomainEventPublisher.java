package Payments.infrastructure.out;

import Payments.application.domain.model.PaymentEvent;
import Payments.application.ports.out.http.IDomainEventPublisher;
import SharedKernel.DomainEvent;
import SharedKernel.EventBus;

public class DomainEventPublisher
		implements IDomainEventPublisher {

	private final EventBus eventBus;

	public DomainEventPublisher(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void publish(DomainEvent event) {
		eventBus.publish(event);
	}
}