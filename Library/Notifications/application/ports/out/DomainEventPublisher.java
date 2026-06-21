package Notifications.application.ports.out;

public interface DomainEventPublisher {

	/**
	 * 
	 * @param event
	 */
	void publish(DomainEvent event);

	void subscribe();

	void reset();

}