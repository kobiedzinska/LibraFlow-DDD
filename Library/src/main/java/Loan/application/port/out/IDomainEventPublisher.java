package Loan.application.port.out;

import Notifications.application.domain.model.DomainEvent;

public interface IDomainEventPublisher {

	void publish(Object event);

	void subscribe();

	void reset();

}