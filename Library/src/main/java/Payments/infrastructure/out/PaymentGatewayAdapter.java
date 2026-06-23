package Payments.infrastructure.out;

import Payments.application.domain.model.*;
import Payments.application.ports.out.http.IPaymentGatewayAdapter;

public class PaymentGatewayAdapter implements IPaymentGatewayAdapter {

	/**
	 * 
	 * @param paymentId
	 * @param amount
	 */
	public boolean processExternalPayment(int paymentId, double amount) {
		return true;
	}

	/**
	 * 
	 * @param paymentId
	 */
	public PaymentStatus verifyPayment(int paymentId) {
		return PaymentStatus.PAID;
	}

}