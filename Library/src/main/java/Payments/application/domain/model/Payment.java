package Payments.application.domain.model;

import java.time.LocalDateTime;

public class Payment {

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	private int paymentId;
	private int readerId;
	private int loanId;
	private double amount;

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

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

	@Override
	public String toString() {
		return paymentId + ";" +
				readerId + ";" +
				loanId + ";" +
				amount + ";" +
				dueDate + ";" +
				paymentStatus;
	}

	public int getLoanId() {
		return loanId;
	}
	public int getReaderId() {
		return readerId;
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}