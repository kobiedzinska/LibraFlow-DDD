package Payments.application.ports.out;

import Payments.application.domain.model.*;

public interface IPaymentGatewayAdapter {

	/**
	 * 
	 * @param paymentId
	 * @param amount
	 */
	boolean processExternalPayment(int paymentId, decimal amount);

	/**
	 * 
	 * @param paymentId
	 */
	PaymentStatus verifyPayment(int paymentId);

}