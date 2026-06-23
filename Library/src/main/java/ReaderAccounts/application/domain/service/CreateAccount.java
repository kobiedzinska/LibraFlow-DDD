package ReaderAccounts.application.domain.service;

import ReaderAccounts.application.domain.model.*;

public class CreateAccount {

	/**
	 *
	 */
	public Boolean checkUnique(boolean loginTaken, boolean emailTaken) {
		return !loginTaken && !emailTaken;
	}

	/**
	 * 
	 * @param librarian
	 */
	public Librarian createLibrarian(Librarian librarian) {
		// TODO - implement CreateAccount.createLibrarian
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param reader
	 */
	public Reader createReader(Reader reader) {
		return reader;
	}

}