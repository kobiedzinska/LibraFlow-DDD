package Payments.application.ports.out.http;

import Payments.application.domain.model.*;

public interface IPaymentGatewayAdapter {

	/**
	 * 
	 * @param paymentId
	 * @param amount
	 */
	boolean processExternalPayment(int paymentId, double amount);

	/**
	 * 
	 * @param paymentId
	 */
	PaymentStatus verifyPayment(int paymentId);

}