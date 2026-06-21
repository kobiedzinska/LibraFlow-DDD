package Notifications.application.ports.out;

import Notifications.application.domain.model.*;

public interface INotificationRepository {

	/**
	 * 
	 * @param n
	 */
	void save(Notification n);

	/**
	 * 
	 * @param nId
	 */
	Notification find(int nId);

}