package Notifications.application.ports.out;

import Notifications.application.domain.model.DomainEvent;

public interface DomainEventPublisher {

	/**
	 * 
	 * @param event
	 */
	void publish(DomainEvent event);

	void subscribe();

	void reset();

}