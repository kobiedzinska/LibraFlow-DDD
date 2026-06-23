package ReaderAccounts.application.service;

import ReaderAccounts.application.domain.model.AccountCreated;
import ReaderAccounts.application.domain.model.AccountDeleted;
import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.ports.in.*;
import ReaderAccounts.application.domain.service.*;
import ReaderAccounts.application.ports.out.http.ICatalogEventPublisher;
import ReaderAccounts.application.ports.out.http.ILoanPort;
import ReaderAccounts.application.ports.out.http.IPaymentsPort;
import ReaderAccounts.application.ports.out.persistence.IReaderRepository;

import java.time.LocalDateTime;

public class ManageAccountsService implements IManageAccountsUseCase {

	private ICatalogEventPublisher domainEventPublisher;
	private IReaderRepository readerRepository;
	private ILoanPort loanPort;
	private IPaymentsPort paymentsPort;
	CreateAccount createAccount;
	DeleteAccount deleteAccount;

	public ManageAccountsService(ICatalogEventPublisher domainEventPublisher, CreateAccount createAccount, DeleteAccount deleteAccount, IReaderRepository readerRepository, ILoanPort loanPort, IPaymentsPort paymentsPort) {
		this.domainEventPublisher = domainEventPublisher;
		this.createAccount = createAccount;
		this.deleteAccount = deleteAccount;
		this.readerRepository = readerRepository;
		this.loanPort = loanPort;
		this.paymentsPort = paymentsPort;
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
		int readerId = reader.getReaderId();

		boolean hasActiveLoans   = loanPort.hasActiveLoans(readerId);
		boolean hasPendingPayments = paymentsPort.hasPendingPayments(readerId);

		if (deleteAccount.canDelete(hasActiveLoans, hasPendingPayments)) {
			//readerRepository.deleteReader(readerId);
			domainEventPublisher.publish(new AccountDeleted(readerId, LocalDateTime.now()));
		} else {
			throw new IllegalArgumentException(
					"Cannot delete account: reader has active loans or pending payments.");
		}
	}


}