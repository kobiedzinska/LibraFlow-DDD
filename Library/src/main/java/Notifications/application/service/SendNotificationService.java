package Notifications.application.service;

import Notifications.application.ports.in.*;
import Notifications.application.ports.out.*;
import Notifications.application.domain.service.*;
import Notifications.application.domain.model.*;

public class SendNotificationService implements ISendNotificationUseCase {

	private DomainEventPublisher domainEventPublisher;
	private INotificationRepository notificationRepository;
	private SendNotification sendNotification;
	private NotificationFactory notificationFactory;

	/**
	 * 
	 * @param command
	 */
	public void sendNotification(SendNotificationCommand command) {

	}

}