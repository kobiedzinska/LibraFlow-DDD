package src.main.java;

import ReaderAccounts.application.ports.out.http.ICatalogEventPublisher;
import ReaderAccounts.application.ports.out.http.ILoanPort;
import ReaderAccounts.application.ports.out.http.IPaymentsPort;
import ReaderAccounts.application.ports.out.persistence.IReaderRepository;
import ReaderAccounts.application.service.ManageAccountsService;
import ReaderAccounts.infrastructure.in.ManageAccountsControler;
import ReaderAccounts.application.domain.service.*;
import ReaderAccounts.infrastructure.out.http.CatalogEventPublisher;
import ReaderAccounts.infrastructure.out.http.LoanPort;
import ReaderAccounts.infrastructure.out.http.PaymentsPort;
import ReaderAccounts.infrastructure.out.persistence.ReaderRepository;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        // Tworzenie instancji zależności
        ICatalogEventPublisher domainEventPublisher = new CatalogEventPublisher();
        IReaderRepository userRepository = new ReaderRepository();
        ILoanPort loanPort = new LoanPort();
        IPaymentsPort paymentsPort = new PaymentsPort();

        CreateAccount createAccount = new CreateAccount();
        DeleteAccount deleteAccount = new DeleteAccount();


        // Tworzenie instancji ManageAccountsService
        ManageAccountsService manageAccountsService = new ManageAccountsService(
                domainEventPublisher, createAccount, deleteAccount, userRepository, loanPort, paymentsPort);

        // Tworzenie instancji ManageAccountsControler z wykorzystaniem interfejsu ManageAccountsUseCase
        ManageAccountsControler controller = new ManageAccountsControler(manageAccountsService);




    }
}