package ReaderAccounts.application.domain.model;

import java.time.LocalDateTime;

public class AccountDeleted extends AccountEvent {

	private int userId;
	private LocalDateTime occuredOn;

	/**
	 * 
	 * @param userId
	 * @param occurredOn
	 */
	public AccountDeleted(int userId, LocalDateTime occurredOn) {
		// TODO - implement AccountDeleted.AccountDeleted
		throw new UnsupportedOperationException();
	}

}