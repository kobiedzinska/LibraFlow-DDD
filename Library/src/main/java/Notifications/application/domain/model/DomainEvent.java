package Notifications.application.domain.model;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    private int notificationId;
    private LocalDateTime occuredAt;
}
