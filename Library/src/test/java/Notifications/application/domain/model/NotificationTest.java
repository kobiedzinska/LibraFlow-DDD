package Notifications.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Notification aggregate (Notification Context / Generic Domain)")
class NotificationTest {

    @Test
    @DisplayName("a new notification starts PENDING and undelivered")
    void newNotificationPending() {
        Notification n = new Notification();
        assertAll(
                () -> assertEquals(NotificationStatus.PENDING, n.getStatus()),
                () -> assertFalse(n.isDelivered())
        );
    }

    @Test
    @DisplayName("the factory produces a pending notification")
    void factoryCreatesPending() {
        assertEquals(NotificationStatus.PENDING,
                new NotificationFactory().createNotification().getStatus());
    }

    @Test
    @DisplayName("marking as sent makes it delivered and records the timestamp")
    void markAsSent() {
        Notification n = new Notification();
        LocalDateTime sentAt = LocalDateTime.now();
        n.markAsSent(sentAt);
        assertAll(
                () -> assertTrue(n.isDelivered()),
                () -> assertEquals(NotificationStatus.SENT, n.getStatus()),
                () -> assertEquals(sentAt, n.getSentAt())
        );
    }

    @Test
    @DisplayName("marking as failed records the reason and is not delivered")
    void markAsFailed() {
        Notification n = new Notification();
        n.markAsFailed("SMTP timeout");
        assertAll(
                () -> assertEquals(NotificationStatus.FAILED, n.getStatus()),
                () -> assertFalse(n.isDelivered()),
                () -> assertEquals("SMTP timeout", n.getFailureReason())
        );
    }

    @Test
    @DisplayName("an already-sent notification cannot be sent again")
    void cannotSendTwice() {
        Notification n = new Notification();
        n.markAsSent(LocalDateTime.now());
        assertThrows(IllegalStateException.class, () -> n.markAsSent(LocalDateTime.now()));
    }
}
