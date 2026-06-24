package Loan.application.port.out;


import SharedKernel.DomainEvent;

public interface IDomainEventPublisher {

	void publish(DomainEvent event);

/*	void reset();*/

}