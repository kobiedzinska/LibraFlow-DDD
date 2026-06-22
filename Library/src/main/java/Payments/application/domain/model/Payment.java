package Payments.application.domain.model;

import java.time.LocalDateTime;

public class Payment {

	private int paymentId;
	private int readerId;
	private int loanId;
	private double amount;
	private LocalDateTime dueDate;
	private PaymentStatus paymentStatus;

	/**
	 * 
	 * @param readerId
	 */

	public Payment(int readerId, int loanId, double fine) {
		this.readerId = readerId;
		this.amount = fine;
		this.loanId = loanId;
		this.paymentStatus = PaymentStatus.PENDING;
		paymentId = -1;
	}

	public void complete() {
		this.paymentStatus = PaymentStatus.PAID;

	}

	public void markedOverdue() {
		this.paymentStatus = PaymentStatus.OVERDUE;
	}

}