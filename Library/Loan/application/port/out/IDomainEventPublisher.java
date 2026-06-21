package Loan.application.port.out;

public interface IDomainEventPublisher {

	void publish();

	void subscribe();

	void reset();

}