package Payments.infrastructure.out;

import Payments.application.ports.out.*;
import Payments.application.domain.model.*;

public class PaymentGatewayAdapter implements IPaymentGatewayAdapter {

	/**
	 * 
	 * @param paymentId
	 * @param amount
	 */
	public boolean processExternalPayment(int paymentId, decimal amount) {
		// TODO - implement PaymentGatewayAdapter.processExternalPayment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param paymentId
	 */
	public PaymentStatus verifyPayment(int paymentId) {
		// TODO - implement PaymentGatewayAdapter.verifyPayment
		throw new UnsupportedOperationException();
	}

}