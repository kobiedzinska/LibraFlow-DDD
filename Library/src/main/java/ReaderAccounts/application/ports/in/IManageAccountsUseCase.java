package ReaderAccounts.application.ports.in;

import ReaderAccounts.application.domain.model.AccountEvent;
import ReaderAccounts.application.domain.model.Reader;

public interface IManageAccountsUseCase {

	void createAccount(Reader reader);

	void deleteAccount(Reader reader);


}