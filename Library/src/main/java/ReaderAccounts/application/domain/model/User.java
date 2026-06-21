package ReaderAccounts.application.domain.model;

import java.time.LocalDateTime;

public abstract class User {

	private String login;
	private String password;
	private String email;
	private LocalDateTime createdAt;
	Profile profile;

}