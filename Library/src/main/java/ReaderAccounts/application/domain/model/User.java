package ReaderAccounts.application.domain.model;

import java.time.LocalDateTime;

public abstract class User {

	private String login;
	private String password;
	private String email;
	private LocalDateTime createdAt;
	Profile profile;

	protected User(String login, String password, String email, LocalDateTime createdAt, Profile profile) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
		this.profile = profile;
	}

	public User() {

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}