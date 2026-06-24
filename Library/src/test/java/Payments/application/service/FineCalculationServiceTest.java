package Payments.application.service;

import Payments.application.domain.model.Payment;
import Payments.application.domain.model.PaymentEvent;
import Payments.application.domain.model.PaymentStatus;
import Payments.application.ports.out.http.IDomainEventPublisher;
import Payments.application.ports.out.persistence.IPaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * PU2 — when Lending reports an overdue loan, Payments creates a pending fine
 * Payment, persists it and publishes the resulting domain event.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FineCalculationService use case (Payment Context)")
class FineCalculationServiceTest {

    @Mock IPaymentRepository paymentRepository;
    @Mock IDomainEventPublisher domainEventPublisher;

    @Test
    @DisplayName("overdue loan creates a pending fine, saves it and publishes an event")
    void handleLoanOverdueCreatesFine() {
        FineCalculationService service =
                new FineCalculationService(paymentRepository, domainEventPublisher);

        // setPaymentId raises the PaymentPending event the service later publishes;
        // simulate the repository assigning an id on save.
        doAnswer(inv -> {
            Payment p = inv.getArgument(0);
            p.setPaymentId(100);
            return null;
        }).when(paymentRepository).savePayment(any(Payment.class));

        LocalDateTime due = LocalDateTime.now().minusDays(10);
        LocalDateTime ret = LocalDateTime.now();

        service.handleLoanOverdue(due, ret, 42, 7);

        ArgumentCaptor<Payment> saved = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).savePayment(saved.capture());
        Payment payment = saved.getValue();

        assertAll(
                () -> assertEquals(42, payment.getReaderId()),
                () -> assertEquals(7, payment.getLoanId()),
                () -> assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus()),
                () -> assertTrue(payment.getAmount() > 0)
        );
        verify(domainEventPublisher).publish(any(PaymentEvent.class));
    }
}
