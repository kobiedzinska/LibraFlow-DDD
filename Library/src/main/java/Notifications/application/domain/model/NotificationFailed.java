package Notifications.application.domain.model;

import java.time.LocalDateTime;

public class NotificationFailed extends DomainEvent {

	private int notificationId;
	private String reason;
	private LocalDateTime occuredAt;

}