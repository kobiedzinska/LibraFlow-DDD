package Payments.application.service;

import Payments.application.ports.in.*;
import Payments.application.ports.out.*;

public class FinePaymentService implements IPaymentUseCase {

	private IPaymentRepository paymentRepository;
	private IDomainEventPublisher domainEventPublisher;

	public void processPayment() {
		// TODO - implement FinePaymentService.processPayment
		throw new UnsupportedOperationException();
	}

	public void checkPaymentStatus() {
		// TODO - implement FinePaymentService.checkPaymentStatus
		throw new UnsupportedOperationException();
	}

}