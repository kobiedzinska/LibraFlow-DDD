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
import Catalog.application.ports.in.ICatalogPort;
import Catalog.application.ports.out.ICopyRepository;
import Catalog.application.service.CopyStatusService;
import Catalog.infrastructure.in.LoanEventListener;
import Catalog.infrastructure.out.CopyRepository;
import Loan.application.domain.service.LoanCopyPolicy;
import Loan.application.port.out.*;
import Loan.application.service.LoanService;
import Loan.infrastructure.in.LoanController;
import Loan.infrastructure.out.*;

public class Main {
    public static void main(String[] args) {        ICatalogEventPublisher domainEventPublisher = new CatalogEventPublisher();
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




        ILoanRepository loanRepository = new LoanRepository();
        IDomainEventPublisher domainEventPublisher = new DomainEventPublisher();
        LoanCopyPolicy loanCopyPolicy = new LoanCopyPolicy();
        IPaymentPort paymentPort = new PaymentPort();
        IReaderSnapshotAdapter readerSnapshotAdapter = new ReaderSnapshotAdapter();



        ICopyRepository copyRepository = new CopyRepository();
        CopyStatusService copyStatusService = new CopyStatusService(copyRepository);
        ICatalogPort catalogCopyQuery = new CopyStatusService(copyRepository);
        ICopyStatusAdapter copyStatusAdapter = new CopyStatusAdapter(catalogCopyQuery);

        LoanEventListener loanEventListener = new LoanEventListener(copyStatusService);
        domainEventPublisher.subscribe(loanEventListener);

        LoanService loanService = new LoanService(loanRepository, domainEventPublisher, loanCopyPolicy, paymentPort, readerSnapshotAdapter, copyStatusAdapter);

        LoanController loanController = new LoanController(loanService);
        //============ Wypożyczenie
        loanController.loanCopy(1, 1);



        //============ Zwrot wypożyczenia
        loanController.returnLoan(1);

    }
}