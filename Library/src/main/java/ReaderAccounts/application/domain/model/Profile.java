package ReaderAccounts.application.domain.model;

public class Profile {

	private String name;
	private String surname;
	private String PESEL;

	public Profile(String name, String surname, String PESEL) {
		this.name = name;
		this.surname = surname;
		this.PESEL = PESEL;
	}
	public Profile() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPESEL() {
		return PESEL;
	}

	public void setPESEL(String PESEL) {
		this.PESEL = PESEL;
	}
}