package ReaderAccounts.infrastructure.in;

import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.ports.in.*;

public class ManageAccountsControler {


	private IManageAccountsUseCase manageAccount;

	public ManageAccountsControler(IManageAccountsUseCase manageAccount) {
		this.manageAccount = manageAccount;
	}
	public void createReaderAccount(Reader reader) {
		manageAccount.createAccount(reader);
	}

	public void deleteReaderAccount(Reader reader) {
		manageAccount.deleteAccount(reader);
	}
}