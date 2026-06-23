package src.main.java;

import ReaderAccounts.application.service.ManageAccountsService;
import ReaderAccounts.infrastructure.in.ManageAccountsControler;
import ReaderAccounts.application.ports.out.*;
import ReaderAccounts.application.domain.service.*;
import ReaderAccounts.infrastructure.out.CatalogEventPublisher;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        // Tworzenie instancji zależności
        ICatalogEventPublisher domainEventPublisher = new CatalogEventPublisher();
        IUserRepository userRepository = new ReaderRepository();

        CreateAccount createAccount = new CreateAccount();
        DeleteAccount deleteAccount = new DeleteAccount();


        // Tworzenie instancji ManageAccountsService
        ManageAccountsService manageAccountsService = new ManageAccountsService(
                domainEventPublisher, createAccount, deleteAccount, userRepository);

        // Tworzenie instancji ManageAccountsControler z wykorzystaniem interfejsu ManageAccountsUseCase
        ManageAccountsControler controller = new ManageAccountsControler(manageAccountsService);



    }
}