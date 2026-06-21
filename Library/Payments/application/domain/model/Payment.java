package Payments.application.domain.model;

public class Payment {

	private int paymentId;
	private int readerId;
	private decimal amount;
	private LocalDateTime dueDate;
	private PaymentStatus paymentStatus;

	/**
	 * 
	 * @param readerId
	 */
	public Payment(int readerId) {
		// TODO - implement Payment.Payment
		throw new UnsupportedOperationException();
	}

	public void complete() {
		// TODO - implement Payment.complete
		throw new UnsupportedOperationException();
	}

	public void markedOverdue() {
		// TODO - implement Payment.markedOverdue
		throw new UnsupportedOperationException();
	}

}