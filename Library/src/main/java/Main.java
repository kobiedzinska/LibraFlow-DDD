import Catalog.application.ports.in.ICopyStatusEventListener;
import Catalog.application.service.CopyStatusQueryService;
import Catalog.infrastructure.in.CatalogAdapter;
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
import Catalog.infrastructure.in.LoanEventListener;
import Catalog.infrastructure.out.CopyRepository;
import Loan.application.domain.service.LoanCopyPolicy;
import Loan.application.port.out.*;
import Loan.application.service.LoanService;
import Loan.infrastructure.in.LoanController;
import Loan.infrastructure.out.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
// ===================== OUTSIDE SERVICES =====================
        ICatalogEventPublisher catalogDomainEventPublisher = new CatalogEventPublisher();
        IReaderRepository userRepository = new ReaderRepository();
        ILoanPort loanPort = new LoanPort();
        IPaymentsPort paymentsPort = new PaymentsPort();

        ILoanRepository loanRepository = new LoanRepository();
        IDomainEventPublisher loanDomainEventPublisher = new DomainEventPublisher();
        IPaymentPort paymentPort = new PaymentAdapter();

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

        controller.createReaderAccount(new Reader(
                "adam",
                "adam",
                "adam@adam.adam",
                LocalDateTime.now(),
                null,
                -1,
                false
        ));

        // ===================== CATALOG MODULE =====================
        ICatalogPort catalogQueryService =
                new CopyStatusQueryService(copyRepository);

        ICopyStatusEventListener copyStatusService =
                new CopyStatusService(copyRepository);

        LoanEventListener loanEventListener =
                new LoanEventListener(copyStatusService);

        loanDomainEventPublisher.subscribe(loanEventListener);

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

        // ===================== FLOW =====================
        loanController.loanCopy(1, 1);


        loanController.returnLoan(1);

    }
}