package Payments.application.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Payment {
	public List<PaymentEvent> getDomainEvents() {
		return paymentEvents;
	}

	public void setPaymentEvents(List<PaymentEvent> domainEvents) {
		this.paymentEvents = domainEvents;
	}

	private List<PaymentEvent> paymentEvents = new ArrayList<>();

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


	public Payment(int paymentId, int readerId, int loanId, double amount, LocalDateTime dueDate, PaymentStatus paymentStatus) {
		this.paymentId = paymentId;
		this.readerId = readerId;
		this.loanId = loanId;
		this.amount = amount;
		this.dueDate = dueDate;
		this.paymentStatus = paymentStatus;
	}

	public PaymentCompleted complete() {
		this.paymentStatus = PaymentStatus.PAID;
		PaymentCompleted event = new PaymentCompleted(paymentId, readerId, LocalDateTime.now());
		paymentEvents.add(event);
		return event;
	}

	public PaymentOverdue markedOverdue() {
		this.paymentStatus = PaymentStatus.OVERDUE;
		PaymentOverdue event = new PaymentOverdue(paymentId, readerId, LocalDateTime.now());
		paymentEvents.add(event);
		return event;
	}


	public static Payment create(int readerId, int loanId, double amount) {
		return new Payment(
				-1,
				readerId,
				loanId,
				amount,
				LocalDateTime.now().plusDays(30),
				PaymentStatus.PENDING
		);
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
	public PaymentPending setPaymentId(int paymentId) {
		this.paymentId = paymentId;
		// Po zapisaniu Payment do repoistory, tworzymy wydarzenie
		PaymentPending event = new PaymentPending(paymentId, readerId, loanId, amount);
		paymentEvents.add(event);
		return event;
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

	public void setStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}