package Payments.application.domain.model;

import java.time.LocalDateTime;

public class PaymentCompleted extends PaymentEvent {

	private int paymentId;
	private int readerId;

	private LocalDateTime occuredOn;

	/**
	 * 
	 * @param paymentId
	 * @param occuredOn
	 */

	public PaymentCompleted(int paymentId, int readerId,  LocalDateTime occuredOn) {
		this.paymentId = paymentId;
		this.readerId = readerId;
		this.occuredOn = occuredOn;
	}
}