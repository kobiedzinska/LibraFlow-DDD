package Payments.application.ports.out;

import Payments.application.domain.model.*;

public interface IPaymentRepository {

	void savePayment();

	/**
	 * 
	 * @param readerId
	 * @param loanId
	 */
	Payment findPayment(int readerId, int loanId);

}