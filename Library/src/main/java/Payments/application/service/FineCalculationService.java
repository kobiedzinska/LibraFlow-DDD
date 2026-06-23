package Payments.application.service;

import Payments.application.domain.model.Payment;
import Payments.application.ports.in.*;
import Payments.application.domain.service.*;
import Payments.application.ports.out.http.IDomainEventPublisher;
import Payments.application.ports.out.persistence.IPaymentRepository;


import java.time.LocalDateTime;

public class FineCalculationService implements ILoanOverdueEventListener {

	private CalculateFine calculateFine;
	private IPaymentRepository paymentRepository;

	private IDomainEventPublisher domainEventPublisher;
	public FineCalculationService(IPaymentRepository paymentRepository, IDomainEventPublisher domainEventPublisher) {
		this.paymentRepository = paymentRepository;
		this.domainEventPublisher = domainEventPublisher;
	}

	/**
	 * 
	 * @param dueDate
	 * @param returnDate
	 */

	@Override
	public void handleLoanOverdue(LocalDateTime dueDate, LocalDateTime returnDate, int clientId, int loanId) {
		// TODO - implement FineCalculationService.handleLoanOverdue

		// Obliczenie kwoty pending, potrzebujemy reader ID i loan ID Calculator
		calculateFine = new CalculateFine();
		double fine = calculateFine.calculate(dueDate, returnDate);
		// Tworzymy Payment
		Payment payment = new Payment(clientId, loanId, fine);
		// Dodajemy do repistory
		paymentRepository.savePayment(payment);


		domainEventPublisher.publish(payment.getDomainEvents().get(0));


	}
}