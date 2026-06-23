package Payments.application.ports.out.persistence;

import Payments.application.domain.model.*;

public interface IPaymentRepository {

	void savePayment(Payment p);

	/**
	 * 
	 * @param paymentId
	 */
	Payment findPayment(int paymentId);

}