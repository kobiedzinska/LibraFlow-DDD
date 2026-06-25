package Loan.application.service;

import Loan.application.domain.model.*;
import Loan.application.domain.service.LoanCopyPolicy;
import Loan.application.port.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Use-case test for the Lending application service. Real LoanCopyPolicy is used;
 * all output ports are mocked and injected through the constructor.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LoanService use case (application layer, Lending Context)")
class LoanServiceTest {

    @Mock ILoanRepository loanRepository;
    @Mock IDomainEventPublisher eventPublisher;
    @Mock IPaymentPort paymentPort;
    @Mock IReaderSnapshotPort readerSnapshotPort;
    @Mock ICopyStatusPort copyStatusPort;

    private LoanService service;

    @BeforeEach
    void setUp() {
        service = new LoanService(loanRepository, eventPublisher, new LoanCopyPolicy(),
                paymentPort, readerSnapshotPort, copyStatusPort);
    }

    private ReaderSnapshot reader(boolean blocked) {
        ReaderSnapshot r = new ReaderSnapshot();
        r.setBlocked(blocked);
        r.setActiveLoansCount(0);
        return r;
    }

    @Test
    @DisplayName("PU1 — eligible reader: saves a new loan and publishes CopyBorrowed")
    void loanCopyHappyPath() throws Exception {
        when(readerSnapshotPort.getReaderSnapshot(42)).thenReturn(reader(false));
        when(loanRepository.countActiveLoansByReaderId(42)).thenReturn(1);
        when(copyStatusPort.getCopyStatus(100)).thenReturn(CopyAvailability.AVAILABLE);

        service.loanCopy(100, 42);

        verify(loanRepository).saveLoan(any(Loan.class));
        verify(eventPublisher).publish(any(CopyBorrowed.class));
    }

    @Test
    @DisplayName("PU1 — blocked reader: saves nothing and publishes nothing")
    void loanCopyBlockedReader() throws Exception {
        when(readerSnapshotPort.getReaderSnapshot(42)).thenReturn(reader(true));
        when(loanRepository.countActiveLoansByReaderId(42)).thenReturn(0);
        when(copyStatusPort.getCopyStatus(100)).thenReturn(CopyAvailability.AVAILABLE);

        service.loanCopy(100, 42);

        verify(loanRepository, never()).saveLoan(any());
        verify(eventPublisher, never()).publish(any());
    }

    @Test
    @DisplayName("PU1 — unavailable copy: saves nothing")
    void loanCopyUnavailableCopy() throws Exception {
        when(readerSnapshotPort.getReaderSnapshot(42)).thenReturn(reader(false));
        when(loanRepository.countActiveLoansByReaderId(42)).thenReturn(0);
        when(copyStatusPort.getCopyStatus(100)).thenReturn(CopyAvailability.BORROWED);

        service.loanCopy(100, 42);

        verify(loanRepository, never()).saveLoan(any());
    }

    @Test
    @DisplayName("PU2 — on-time return publishes only CopyReturned and saves the loan")
    void returnLoanOnTime() {
        Loan loan = new Loan(7, 42, 100,
                LocalDateTime.now().plusDays(10), LocalDateTime.now().minusDays(1),
                LoanStatus.BORROWED, null);
        when(loanRepository.findLoan(7)).thenReturn(loan);

        service.returnLoan(7);

        verify(loanRepository).saveLoan(loan);
        verify(eventPublisher).publish(any(CopyReturned.class));
        verify(eventPublisher, never()).publish(any(LoanReturnedLate.class));
    }

    @Test
    @DisplayName("PU2 — late return publishes CopyReturned and then LoanReturnedLate")
    void returnLoanLate() {
        Loan loan = new Loan(7, 42, 100,
                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(32),
                LoanStatus.BORROWED, null);
        when(loanRepository.findLoan(7)).thenReturn(loan);

        service.returnLoan(7);

        verify(eventPublisher).publish(any(CopyReturned.class));
        verify(eventPublisher).publish(any(LoanReturnedLate.class));
    }
}
