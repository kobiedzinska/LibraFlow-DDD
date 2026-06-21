package Notifications.application.domain.model;

import Notifications.application.domain.service.*;

import java.time.LocalDateTime;

public class Notification {

	private int notificationId;
	private Recipent recipent;
	private Channel channel;
	private MessageTemplate template;
	private NotificationStatus status;
	private NotificationMessage message;
	private LocalDateTime createdAt;
	private LocalDateTime sentAt;

	/**
	 * 
	 * @param message
	 */
	public void send(SendNotification message) {

	}

	/**
	 * 
	 * @param sentAt
	 */
	public void markAsSent(LocalDateTime sentAt) {
		// TODO - implement Notification.markAsSent
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param reason
	 */
	public void markAsFailed(String reason) {
		// TODO - implement Notification.markAsFailed
		throw new UnsupportedOperationException();
	}

	public boolean isDelivered() {
		// TODO - implement Notification.isDelivered
		throw new UnsupportedOperationException();
	}

}