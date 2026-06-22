package Payments.infrastructure.out;

import Payments.application.domain.model.*;
import Payments.application.ports.out.persistence.IPaymentRepository;

public class PaymentRepository implements IPaymentRepository {

	@Override
	public void savePayment(Payment p) {

	}

	/**
	 * 
	 * @param readerId
	 * @param loanId
	 */
	public Payment findPayment(int readerId, int loanId) {
		// TODO - implement PaymentRepository.findPayment
		throw new UnsupportedOperationException();
	}

}