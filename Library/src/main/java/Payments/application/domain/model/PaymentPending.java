package Payments.application.domain.model;

public class PaymentPending extends PaymentEvent {
    private final int paymentId;
    private final int clientId;
    private final int loanId;
    private final double amount;

    public PaymentPending(int paymentId, int clientId, int loanId, double amount) {
        this.paymentId = paymentId;
        this.clientId = clientId;
        this.loanId = loanId;
        this.amount = amount;
    }


}