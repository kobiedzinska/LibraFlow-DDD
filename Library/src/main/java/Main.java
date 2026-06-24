import Catalog.application.ports.out.ICopyRepository;
import Catalog.application.service.CopyStatusService;
import Catalog.infrastructure.in.LoanEventListener;
import Catalog.infrastructure.out.CopyRepository;
import Loan.application.domain.service.LoanCopyPolicy;
import Loan.application.port.out.*;
import Loan.application.service.LoanService;
import Loan.infrastructure.in.LoanController;
import Loan.infrastructure.out.*;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ILoanRepository loanRepository = new LoanRepository();
        IDomainEventPublisher domainEventPublisher = new DomainEventPublisher();
        LoanCopyPolicy loanCopyPolicy = new LoanCopyPolicy();
        IPaymentPort paymentPort = new PaymentPort();
        IReaderSnapshotAdapter readerSnapshotAdapter = new ReaderSnapshotAdapter();
        ICopyStatusAdapter copyStatusAdapter = new CopyStatusAdapter();


        ICopyRepository copyRepository = new CopyRepository();
        CopyStatusService copyStatusService = new CopyStatusService(copyRepository);


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
