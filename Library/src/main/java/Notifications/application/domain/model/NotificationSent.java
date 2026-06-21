package Notifications.application.domain.model;

import java.time.LocalDateTime;

public class NotificationSent {

	private int notificationId;
	private int recipentId;
	private Channel channel;
	private LocalDateTime sentAt;
	private int attribute;

}