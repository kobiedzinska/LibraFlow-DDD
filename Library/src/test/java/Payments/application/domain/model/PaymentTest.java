package Payments.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Payment aggregate (Payment Context / Generic Domain)")
class PaymentTest {

    private Payment newPayment() {
        return new Payment(42, 7, 5.0);
    }

    @Test
    @DisplayName("a new payment is PENDING and keeps reader/loan/amount (PU2)")
    void newPaymentIsPending() {
        Payment payment = newPayment();

        assertAll(
                () -> assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus()),
                () -> assertEquals(42, payment.getReaderId()),
                () -> assertEquals(7, payment.getLoanId()),
                () -> assertEquals(5.0, payment.getAmount(), 1e-9),
                () -> assertTrue(payment.getDomainEvents().isEmpty())
        );
    }

    @Test
    @DisplayName("setPaymentId records a PaymentPending domain event")
    void setPaymentIdRaisesPending() {
        Payment payment = newPayment();

        PaymentPending event = payment.setPaymentId(100);

        assertAll(
                () -> assertNotNull(event),
                () -> assertEquals(100, payment.getPaymentId()),
                () -> assertEquals(1, payment.getDomainEvents().size())
        );
    }

    @Test
    @DisplayName("complete() moves to PAID and returns a PaymentCompleted event (PU4)")
    void completeMovesToPaid() {
        Payment payment = newPayment();

        PaymentCompleted event = payment.complete();

        assertAll(
                () -> assertEquals(PaymentStatus.PAID, payment.getPaymentStatus()),
                () -> assertNotNull(event),
                () -> assertTrue(payment.getDomainEvents().contains(event))
        );
    }

    @Test
    @DisplayName("markedOverdue() moves to OVERDUE and returns a PaymentOverdue event")
    void markOverdue() {
        Payment payment = newPayment();

        PaymentOverdue event = payment.markedOverdue();

        assertAll(
                () -> assertEquals(PaymentStatus.OVERDUE, payment.getPaymentStatus()),
                () -> assertNotNull(event),
                () -> assertTrue(payment.getDomainEvents().contains(event))
        );
    }
}
