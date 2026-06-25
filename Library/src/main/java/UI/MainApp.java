package UI;

import Catalog.application.ports.in.*;
import Catalog.application.ports.out.IBookRepository;
import Catalog.application.ports.out.ICopyRepository;
import Catalog.application.service.BookQueryService;
import Catalog.application.service.CopyStatusQueryService;
import Catalog.application.service.CopyStatusService;
import Catalog.application.service.ManageCatalogService;
import Catalog.infrastructure.in.CopyBorrowedHandler;
import Catalog.infrastructure.in.CopyOverdueHandler;
import Catalog.infrastructure.in.CopyReturnedHandler;
import Catalog.infrastructure.in.UIBrowseAdapter;
import Catalog.infrastructure.out.BookRepository;
import Catalog.infrastructure.out.CopyRepository;
import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;
import Loan.application.domain.model.LoanOverdue;
import Loan.application.domain.service.LoanCopyPolicy;
import Loan.application.port.out.*;
import Loan.application.service.LoanService;
import Loan.infrastructure.in.LoanController;
import Loan.infrastructure.out.*;
import Payments.application.domain.model.PaymentCompleted;
import Payments.application.ports.in.ILoanOverdueEventListener;
import Payments.application.ports.in.IPaymentUseCase;
import Payments.application.ports.out.persistence.IPaymentRepository;
import Payments.application.service.FineCalculationService;
import Payments.application.service.FinePaymentService;
import Payments.infrastructure.in.LoanOverdueHandler;
import Payments.infrastructure.in.PaymentController;
import Payments.infrastructure.out.persistence.PaymentRepository;
import ReaderAccounts.application.domain.service.CreateAccount;
import ReaderAccounts.application.domain.service.DeleteAccount;
import ReaderAccounts.application.ports.in.IAccountReaderPort;
import ReaderAccounts.application.ports.out.http.ICatalogEventPublisher;
import ReaderAccounts.application.ports.out.http.ILoanPort;
import ReaderAccounts.application.ports.out.http.IPaymentsPort;
import ReaderAccounts.application.ports.out.persistence.IReaderRepository;
import ReaderAccounts.application.service.ManageAccountsService;
import ReaderAccounts.application.service.ReaderStatusQueryService;
import ReaderAccounts.infrastructure.in.ManageAccountsControler;
import ReaderAccounts.infrastructure.out.http.CatalogEventPublisher;
import ReaderAccounts.infrastructure.out.http.LoanPort;
import ReaderAccounts.infrastructure.out.http.PaymentsPort;
import ReaderAccounts.infrastructure.out.persistence.ReaderRepository;
import SharedKernel.EventBus;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class MainApp extends Application {

    public static Stack<Scene> stack =new Stack<>();
    @FXML
    public TextField loanReaderId;
    @FXML
    public Button loanButton;
    @FXML
    public TextField loanCopyId;
    @FXML
    public TextField returnLoanId;
    @FXML
    public TextField returnLoanStatus;
    @FXML
    public TextField latePaymentId;
    @FXML
    public TextField paymentStatus;
    @FXML
    public Button returnLoanButton;
    @FXML
    public Button payLoanButton;
    public TextField searchTextField;

    @FXML
    private TableView<BookAvailabilityDto> booksTable;

    @FXML
    private TableColumn<BookAvailabilityDto, Integer> idColumn;

    @FXML
    private TableColumn<BookAvailabilityDto, String> titleColumn;

    @FXML
    private TableColumn<BookAvailabilityDto, String> authorColumn;

    @FXML
    private TableColumn<BookAvailabilityDto, Long> availableColumn;
    LoanController loanController;
    EventBus eventBus;
    PaymentController paymentController;
    UIBrowseAdapter uiBrowseAdapter;

    @Override
    public void start(Stage stage) throws IOException {


        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("bookId"));

        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title"));

        authorColumn.setCellValueFactory(
                new PropertyValueFactory<>("author"));

        availableColumn.setCellValueFactory(
                new PropertyValueFactory<>("availableCopies"));





        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/uis/MainView.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stack.push(scene);
        stage.setScene(scene);
        stage.setTitle("LibraFlow");
        stage.show();
    }

    @FXML
    public void initialize(){
         eventBus = new EventBus();

        ICatalogEventPublisher catalogDomainEventPublisher = new CatalogEventPublisher();
        IReaderRepository userRepository = new ReaderRepository();
        ILoanPort loanPort = new LoanPort();
        IPaymentsPort paymentsPort = new PaymentsPort();

        IPaymentRepository paymentRepository = new PaymentRepository();

        Payments.application.ports.out.http.IDomainEventPublisher paymentDomainEventPublisher =
                new Payments.infrastructure.out.DomainEventPublisher(eventBus);

        ILoanOverdueEventListener loanOverdueEventListener =
                new FineCalculationService(paymentRepository, paymentDomainEventPublisher);

        IPaymentUseCase paymentUseCase =
                new FinePaymentService(paymentDomainEventPublisher, paymentRepository);

        paymentController =
                new PaymentController(paymentUseCase);

        ILoanRepository loanRepository = new LoanRepository();

        IDomainEventPublisher loanDomainEventPublisher = new DomainEventPublisher(eventBus);

        IPaymentPort paymentPort = new PaymentAdapter();

        IBookRepository bookRepository = new BookRepository();
        ICopyRepository copyRepository = new CopyRepository();

        // READER
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

        // CATALOG
        ICatalogPort catalogQueryService = new CopyStatusQueryService(copyRepository);

        ICopyStatusEventListener copyStatusService = new CopyStatusService(copyRepository);




        eventBus.register(event -> {
            if (event instanceof CopyReturned e) {
                new UIEventListener(returnLoanStatus).onEvent(e);
            }
        });

        eventBus.register(event -> {
            if (event instanceof PaymentCompleted e) {
                new UIEventListener(paymentStatus).onEvent(e);
            }
        });

        eventBus.register(event -> {
            if (event instanceof LoanOverdue e) {
                new UIEventListener(returnLoanStatus).onEvent(e);
            }
        });



        eventBus.register(new CopyBorrowedHandler(copyStatusService));
        eventBus.register(new CopyOverdueHandler(copyStatusService));
        eventBus.register(new CopyReturnedHandler(copyStatusService));
        eventBus.register(new LoanOverdueHandler(loanOverdueEventListener));

        ICatalogManagementUseCase catalogManagementUseCase =
                new ManageCatalogService(bookRepository, copyRepository);

        // LOAN
        IAccountReaderPort accountReaderPort =
                new ReaderStatusQueryService(userRepository);

        IReaderSnapshotPort readerSnapshotPort =
                new ReaderSnapshotAdapter(accountReaderPort);

        LoanCopyPolicy loanCopyPolicy = new LoanCopyPolicy();

        ICopyStatusPort copyStatusPort =
                new CopyStatusAdapter(catalogQueryService);

        LoanService loanService = new LoanService(
                loanRepository,
                loanDomainEventPublisher,
                loanCopyPolicy,
                paymentPort,
                readerSnapshotPort,
                copyStatusPort
        );

        loanController =
                new LoanController(loanService);

        ICatalogBrowsePort catalogBrowsePort =
                new BookQueryService(bookRepository, copyRepository);

        uiBrowseAdapter =
                new UIBrowseAdapter(catalogBrowsePort);
    }

    public static void main(String[] args) {
        launch();
    }


    public void onLoanButtonClicked(ActionEvent actionEvent) {
        loanController.loanCopy(Integer.parseInt(loanCopyId.getText()), Integer.parseInt(loanReaderId.getText()));
    }

    public void onReturnedLoanClick(ActionEvent actionEvent) {
        loanController.returnLoan(Integer.parseInt(returnLoanId.getText()));
    }

    public void onLoanPaidClick(ActionEvent actionEvent) {
        paymentController.processPayment(Integer.parseInt(latePaymentId.getText()));
    }

    public void onKeyTyped(KeyEvent keyEvent) {
        List<BookAvailabilityDto> books = uiBrowseAdapter.search(searchTextField.toString());
        booksTable.getItems().setAll(books);
    }
}
