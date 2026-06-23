package ReaderAccounts.application.ports.out;

import ReaderAccounts.application.domain.model.*;

public interface IUserRepository {

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