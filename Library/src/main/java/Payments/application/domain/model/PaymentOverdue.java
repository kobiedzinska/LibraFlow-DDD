package Payments.application.domain.model;

import java.time.LocalDateTime;

public class PaymentOverdue extends PaymentEvent {

	private int paymentId;
	private int clientId;
	private LocalDateTime occuredOn;

	/**
	 * 
	 * @param paymentId
	 * @param clientId
	 * @param occuredOn
	 */
	public PaymentOverdue(int paymentId, int clientId, LocalDateTime occuredOn) {
		// TODO - implement PaymentOverdue.PaymentOverdue
		throw new UnsupportedOperationException();
	}

}