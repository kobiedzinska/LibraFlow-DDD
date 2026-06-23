package Payments.infrastructure.out;

import Payments.application.domain.model.PaymentEvent;
import Payments.application.ports.out.http.IDomainEventPublisher;

public class DomainEventPublisher implements IDomainEventPublisher {

	public void publish(PaymentEvent event) {
		// TODO - implement DomainEventPublisher.publish
		throw new UnsupportedOperationException();
	}

	public void subscribe(PaymentEvent event) {
		// TODO - implement DomainEventPublisher.subscribe
		throw new UnsupportedOperationException();
	}

	public void reset(PaymentEvent event) {
		// TODO - implement DomainEventPublisher.reset
		throw new UnsupportedOperationException();
	}


}