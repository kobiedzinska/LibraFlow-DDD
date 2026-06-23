package Payments.application.domain.model;

import java.time.LocalDateTime;

public class PaymentCompleted extends PaymentEvent {

	private int paymentId;
	private LocalDateTime occuredOn;

	/**
	 * 
	 * @param paymentId
	 * @param occuredOn
	 */
	public PaymentCompleted(int paymentId, int readerId, LocalDateTime occuredOn) {
		// TODO - implement PaymentCompleted.PaymentCompleted
		throw new UnsupportedOperationException();
	}

}