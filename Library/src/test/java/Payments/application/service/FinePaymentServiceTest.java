package Payments.application.service;

import Payments.application.domain.model.Payment;
import Payments.application.domain.model.PaymentCompleted;
import Payments.application.domain.model.PaymentEvent;
import Payments.application.domain.model.PaymentStatus;
import Payments.application.ports.out.http.IDomainEventPublisher;
import Payments.application.ports.out.persistence.IPaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * PU4 — paying a fine: the service marks the payment PAID, persists it and
 * publishes a PaymentCompleted event.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FinePaymentService use case (Payment Context)")
class FinePaymentServiceTest {

    @Mock IPaymentRepository paymentRepository;
    @Mock IDomainEventPublisher domainEventPublisher;

    @Test
    @DisplayName("processPayment marks an existing payment PAID and publishes completion")
    void processPaymentHappyPath() {
        FinePaymentService service =
                new FinePaymentService(domainEventPublisher, paymentRepository);
        Payment payment = new Payment(42, 7, 5.0);
        when(paymentRepository.findPayment(100)).thenReturn(payment);

        service.processPayment(100);

        assertEquals(PaymentStatus.PAID, payment.getPaymentStatus());
        verify(paymentRepository).savePayment(payment);
        verify(domainEventPublisher).publish(any(PaymentCompleted.class));
    }

    @Test
    @DisplayName("an unknown payment id raises and publishes nothing")
    void unknownPaymentThrows() {
        FinePaymentService service =
                new FinePaymentService(domainEventPublisher, paymentRepository);
        when(paymentRepository.findPayment(999)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.processPayment(999));
        verify(domainEventPublisher, never()).publish(any(PaymentEvent.class));
    }
}
