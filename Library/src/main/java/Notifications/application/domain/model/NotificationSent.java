package Notifications.application.domain.model;

import java.time.LocalDateTime;

public class NotificationSent extends DomainEvent {

	private int notificationId;
	private int recipentId;
	private Channel channel;
	private LocalDateTime occuredAt;
	private int attribute;

}