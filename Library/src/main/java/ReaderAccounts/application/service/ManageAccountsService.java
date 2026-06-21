package ReaderAccounts.application.service;

import ReaderAccounts.application.ports.in.*;
import ReaderAccounts.application.ports.out.*;
import ReaderAccounts.application.domain.service.*;

public class ManageAccountsService implements ManageAccountsUseCase {

	private IDomainEventPublisher domainEventPublisher;
	CreateAccount createAccount;
	UpdateAccount updateAccount;
	DeleteAccount deleteAccount;

	public void createAccount() {
		// TODO - implement ManageAccountsService.createAccount
		throw new UnsupportedOperationException();
	}

	public void deleteAccount() {
		// TODO - implement ManageAccountsService.deleteAccount
		throw new UnsupportedOperationException();
	}

	public void updateAccount() {
		// TODO - implement ManageAccountsService.updateAccount
		throw new UnsupportedOperationException();
	}

}