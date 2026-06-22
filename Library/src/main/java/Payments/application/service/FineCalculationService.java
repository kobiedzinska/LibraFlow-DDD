package Payments.application.service;

import Payments.application.domain.model.Payment;
import Payments.application.ports.in.*;
import Payments.application.domain.service.*;
import Payments.application.ports.out.http.IDomainEventPublisher;


import java.time.LocalDateTime;

public class FineCalculationService implements ILoanOverdueEventListener {

	private CalculateFine calculateFine;

	private IDomainEventPublisher domainEventPublisher;

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


		throw new UnsupportedOperationException();
	}
}