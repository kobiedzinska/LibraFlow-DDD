package ReaderAccounts.application.domain.model;

public class AccountUpdated {

	private LocalDateTime occuredOn;
	private int userId;
	private int changedFields;

	/**
	 * 
	 * @param userId
	 * @param changedFields
	 * @param occurredOn
	 */
	public AccountUpdated(int userId, int changedFields, LocalDateTime occurredOn) {
		// TODO - implement AccountUpdated.AccountUpdated
		throw new UnsupportedOperationException();
	}

}