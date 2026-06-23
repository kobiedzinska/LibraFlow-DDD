package ReaderAccounts.application.domain.model;

import java.time.LocalDateTime;

public class Reader extends User {

	private int readerId;
	private Boolean isBlocked;

	public Reader(String login, String password, String email,
				  LocalDateTime createdAt, Profile profile,
				  int readerId, Boolean isBlocked) {
		super(login, password, email, createdAt, profile);
		this.readerId = readerId;
		this.isBlocked = isBlocked;
	}

	public Reader() {
        super();
    }


	public void block() {
		isBlocked = true;
	}

	public void unblock() {
		isBlocked = false;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public Boolean getBlocked() {
		return isBlocked;
	}

	public void setBlocked(Boolean blocked) {
		isBlocked = blocked;
	}
}