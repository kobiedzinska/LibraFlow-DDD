package Payments.application.ports.out;

public interface IDomainEventPublisher {

	void publish();

	void subscribe();

	void reset();

}