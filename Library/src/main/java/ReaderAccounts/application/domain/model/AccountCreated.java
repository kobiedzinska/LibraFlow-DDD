package ReaderAccounts.application.domain.model;

import java.time.LocalDateTime;

public class AccountCreated extends AccountEvent {

	private int userId;
	private Roles role;
	private LocalDateTime occuredOn;

	/**
	 * 
	 * @param userId
	 * @param occurredOn
	 */
	public AccountCreated(int userId, LocalDateTime occurredOn) {
		this.userId = userId;
		this.occuredOn = occurredOn;
	}

}