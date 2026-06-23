package Payments.application.ports.out.http;

public interface IDomainEventPublisher {

	void publish();

	void subscribe();

	void reset();

}