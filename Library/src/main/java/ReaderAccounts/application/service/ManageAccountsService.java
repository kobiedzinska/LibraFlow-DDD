package ReaderAccounts.application.service;

import ReaderAccounts.application.domain.model.AccountCreated;
import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.ports.in.*;
import ReaderAccounts.application.ports.out.*;
import ReaderAccounts.application.domain.service.*;

import java.time.LocalDateTime;

public class ManageAccountsService implements IManageAccountsUseCase {

	private ICatalogEventPublisher domainEventPublisher;
	private IUserRepository readerRepository;
	CreateAccount createAccount;
	DeleteAccount deleteAccount;

	public ManageAccountsService(ICatalogEventPublisher domainEventPublisher, CreateAccount createAccount, DeleteAccount deleteAccount, IUserRepository readerRepository) {
		this.domainEventPublisher = domainEventPublisher;
		this.createAccount = createAccount;
		this.deleteAccount = deleteAccount;
		this.readerRepository = readerRepository;
	}

	public void createAccount(Reader reader) {
		boolean loginTaken = readerRepository.existsByLogin(reader.getLogin());
		boolean emailTaken = readerRepository.existsByEmail(reader.getEmail());

		if (createAccount.checkUnique(loginTaken, emailTaken)) {
			readerRepository.saveReader(reader);
			Reader createdReader = createAccount.createReader(reader);
			// Publish domain event
			domainEventPublisher.publish(new AccountCreated(reader.getReaderId(), LocalDateTime.now()));
		} else {
			throw new IllegalArgumentException("An account with the same login or email already exists.");
		}
	}
	public void deleteAccount(Reader reader) {
		// TODO - implement ManageAccountsService.deleteAccount
		throw new UnsupportedOperationException();
	}


}