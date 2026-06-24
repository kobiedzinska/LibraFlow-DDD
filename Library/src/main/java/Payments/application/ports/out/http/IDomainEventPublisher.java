package Payments.application.ports.out.http;

import Payments.application.domain.model.PaymentEvent;
import SharedKernel.DomainEvent;

public interface IDomainEventPublisher {

	void publish(DomainEvent event);


}