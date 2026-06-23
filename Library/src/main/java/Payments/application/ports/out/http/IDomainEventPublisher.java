package Payments.application.ports.out.http;

import Payments.application.domain.model.PaymentEvent;

public interface IDomainEventPublisher {

	void publish(PaymentEvent event);

	void subscribe(PaymentEvent event);

	void reset(PaymentEvent event);

}