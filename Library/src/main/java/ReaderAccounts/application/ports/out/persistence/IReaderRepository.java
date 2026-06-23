package ReaderAccounts.application.ports.out.persistence;

import ReaderAccounts.application.domain.model.*;

public interface IReaderRepository {

	/**
	 * 
	 * @param r
	 */
	void saveReader(Reader r);

	/**
	 * 
	 * @param rId
	 */
	Reader findUser(int rId);
	boolean existsByLogin(String login);
	boolean existsByEmail(String email);

}