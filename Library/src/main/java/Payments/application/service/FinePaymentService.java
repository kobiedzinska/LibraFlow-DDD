package Payments.application.service;

import Payments.application.domain.model.Payment;
import Payments.application.domain.model.PaymentStatus;
import Payments.application.ports.in.*;
import Payments.application.ports.out.http.IDomainEventPublisher;
import Payments.application.ports.out.http.IPaymentGatewayAdapter;
import Payments.application.ports.out.persistence.IPaymentRepository;
import Payments.infrastructure.out.PaymentGatewayAdapter;

import java.util.NoSuchElementException;

public class FinePaymentService implements IPaymentUseCase {

	private IPaymentRepository paymentRepository;
	private IDomainEventPublisher domainEventPublisher;

	public FinePaymentService(IDomainEventPublisher domainEventPublisher, IPaymentRepository paymentRepository) {
		this.domainEventPublisher = domainEventPublisher;
		this.paymentRepository = paymentRepository;
	}

	public void processPayment(int paymentId) {
		Payment payment = paymentRepository.findPayment(paymentId);

		if (payment == null) {
			throw new IllegalArgumentException("Payment not found with ID: " + paymentId);
		}

		IPaymentGatewayAdapter paymentGatewayAdapter = new PaymentGatewayAdapter();

		if(paymentGatewayAdapter.processExternalPayment(paymentId, payment.getAmount())){
			// Update the payment status to PAID
			payment.setStatus(PaymentStatus.PAID);
		};


		// Save the updated payment to the repository
		paymentRepository.savePayment(payment);

		// Publish a PaymentCompleted domain event
		domainEventPublisher.publish(payment.complete());
	}



}