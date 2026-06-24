import Catalog.application.ports.in.ICatalogBrowsePort;
import Catalog.application.ports.in.ICopyStatusEventListener;
import Catalog.application.ports.out.IBookRepository;
import Catalog.application.service.BookQueryService;
import Catalog.application.service.CopyStatusQueryService;
import Catalog.infrastructure.in.CatalogAdapter;
import Catalog.infrastructure.in.UIBrowseAdapter;
import Catalog.infrastructure.out.BookRepository;
import Payments.application.ports.in.ILoanOverdueEventListener;
import Payments.application.ports.out.persistence.IPaymentRepository;
import Payments.application.service.FineCalculationService;
import Payments.infrastructure.in.LoanOverdueHandler;
import Payments.infrastructure.in.PaymentController;
import Payments.infrastructure.out.persistence.PaymentRepository;
import ReaderAccounts.application.domain.model.Profile;
import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.ports.in.IAccountReaderPort;
import ReaderAccounts.application.ports.out.http.ICatalogEventPublisher;
import ReaderAccounts.application.ports.out.http.ILoanPort;
import ReaderAccounts.application.ports.out.http.IPaymentsPort;
import ReaderAccounts.application.ports.out.persistence.IReaderRepository;
import ReaderAccounts.application.service.ManageAccountsService;
import ReaderAccounts.application.service.ReaderStatusQueryService;
import ReaderAccounts.infrastructure.in.AccountReaderAdapter;
import ReaderAccounts.infrastructure.in.ManageAccountsControler;
import ReaderAccounts.application.domain.service.*;
import ReaderAccounts.infrastructure.out.http.CatalogEventPublisher;
import ReaderAccounts.infrastructure.out.http.LoanPort;
import ReaderAccounts.infrastructure.out.http.PaymentsPort;
import ReaderAccounts.infrastructure.out.persistence.ReaderRepository;
import Catalog.application.ports.in.ICatalogPort;
import Catalog.application.ports.out.ICopyRepository;
import Catalog.application.service.CopyStatusService;
import Catalog.infrastructure.out.CopyRepository;
import Loan.application.domain.service.LoanCopyPolicy;
import Loan.application.port.out.*;
import Loan.application.service.LoanService;
import Loan.infrastructure.in.LoanController;
import Loan.infrastructure.out.*;
import SharedKernel.EventBus;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
// ===================== OUTSIDE SERVICES =====================
        ICatalogEventPublisher catalogDomainEventPublisher = new CatalogEventPublisher();
        IReaderRepository userRepository = new ReaderRepository();
        ILoanPort loanPort = new LoanPort();
        IPaymentsPort paymentsPort = new PaymentsPort();
        EventBus eventBus = new EventBus();
        IPaymentRepository paymentRepository = new PaymentRepository();
        Payments.application.ports.out.http.IDomainEventPublisher paymentDomainEventPublisher = new Payments.infrastructure.out.DomainEventPublisher(eventBus);
        ILoanOverdueEventListener loanOverdueEventListener = new FineCalculationService(paymentRepository, paymentDomainEventPublisher);

        PaymentController paymentController = new PaymentController();
        ILoanRepository loanRepository = new LoanRepository();
        IDomainEventPublisher loanDomainEventPublisher = new DomainEventPublisher(eventBus);
        IPaymentPort paymentPort = new PaymentAdapter();

        IBookRepository bookRepository = new BookRepository();

        ICopyRepository copyRepository = new CopyRepository();

        // ===================== READER MODULE =====================
        CreateAccount createAccount = new CreateAccount();
        DeleteAccount deleteAccount = new DeleteAccount();


        ManageAccountsService manageAccountsService = new ManageAccountsService(
                catalogDomainEventPublisher,
                createAccount,
                deleteAccount,
                userRepository,
                loanPort,
                paymentsPort
        );

        ManageAccountsControler controller =
                new ManageAccountsControler(manageAccountsService);

/*        controller.createReaderAccount(new Reader(
                "adam",
                "adam",
                "adam@adam.adam",
                LocalDateTime.now(),
                null,
                -1,
                false
        ));*/

        // ===================== CATALOG MODULE =====================
        ICatalogPort catalogQueryService =
                new CopyStatusQueryService(copyRepository);

        ICopyStatusEventListener copyStatusService =
                new CopyStatusService(copyRepository);

/*        LoanEventListener loanEventListener =
                new LoanEventListener(copyStatusService);*/

        LoanOverdueHandler loanEventListener = new LoanOverdueHandler(loanOverdueEventListener);
        eventBus.register(loanEventListener);

        // ===================== READER QUERY =====================
        IAccountReaderPort accountReaderPort =
                new ReaderStatusQueryService(userRepository);

        // ===================== LOAN MODULE =====================

        IReaderSnapshotPort readerSnapshotPort = new ReaderSnapshotAdapter(accountReaderPort);
        LoanCopyPolicy loanCopyPolicy = new LoanCopyPolicy();

        ICopyStatusPort copyStatusPort = new CopyStatusAdapter(catalogQueryService);

        LoanService loanService = new LoanService(
                loanRepository,
                loanDomainEventPublisher,
                loanCopyPolicy,
                paymentPort,
                readerSnapshotPort,
                copyStatusPort
        );


        LoanController loanController = new LoanController(loanService);

        ICatalogBrowsePort catalogBrowsePort = new BookQueryService(bookRepository, copyRepository);

        UIBrowseAdapter uiBrowseAdapter = new UIBrowseAdapter(catalogBrowsePort);



        System.out.println(uiBrowseAdapter.search(""));

        // ===================== FLOW =====================
        //loanController.loanCopy(1, 1);


        loanController.returnLoan(3);

    }
}