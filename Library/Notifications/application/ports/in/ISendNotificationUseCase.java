package Notifications.application.ports.in;

public interface ISendNotificationUseCase {

	/**
	 * 
	 * @param command
	 */
	void sendNotification(SendNotificationCommand command);

}