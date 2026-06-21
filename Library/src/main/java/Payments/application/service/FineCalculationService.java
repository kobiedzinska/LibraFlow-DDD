package Payments.application.service;

import Payments.application.ports.in.*;
import Payments.application.domain.service.*;
import Payments.application.ports.out.*;

public class FineCalculationService implements ILoanOverdueEventListener {

	private CalculateFine calculateFine;
	private IPaymentRepository paymentRepository;
	private IDomainEventPublisher domainEventPublisher;

	/**
	 * 
	 * @param dueDate
	 * @param returnDate
	 */
	public void handleLoanOverdue(int dueDate, int returnDate) {
		// TODO - implement FineCalculationService.handleLoanOverdue
		throw new UnsupportedOperationException();
	}

}