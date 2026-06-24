package ReaderAccounts.application.service;

import ReaderAccounts.application.domain.model.AccountCreated;
import ReaderAccounts.application.domain.model.AccountDeleted;
import ReaderAccounts.application.domain.model.AccountEvent;
import ReaderAccounts.application.domain.model.Profile;
import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.domain.service.CreateAccount;
import ReaderAccounts.application.domain.service.DeleteAccount;
import ReaderAccounts.application.ports.out.http.ICatalogEventPublisher;
import ReaderAccounts.application.ports.out.http.ILoanPort;
import ReaderAccounts.application.ports.out.http.IPaymentsPort;
import ReaderAccounts.application.ports.out.persistence.IReaderRepository;
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
 * Use-case test for the Identity context (PU5). Real domain policies are used;
 * only the output ports are mocked. Constructor injection keeps the wiring clean.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ManageAccountsService use case (ReaderAccounts Context)")
class ManageAccountsServiceTest {

    @Mock ICatalogEventPublisher eventPublisher;
    @Mock IReaderRepository readerRepository;
    @Mock ILoanPort loanPort;
    @Mock IPaymentsPort paymentsPort;

    private ManageAccountsService service;

    @BeforeEach
    void setUp() {
        service = new ManageAccountsService(
                eventPublisher, new CreateAccount(), new DeleteAccount(),
                readerRepository, loanPort, paymentsPort);
    }

    private Reader reader() {
        return new Reader("jkowalski", "secret", "j@k.pl",
                LocalDateTime.now(), new Profile("Jan", "Kowalski", "90010112345"),
                5, false);
    }

    @Test
    @DisplayName("createAccount with a free login/email saves the reader and publishes AccountCreated")
    void createAccountHappyPath() {
        Reader r = reader();
        when(readerRepository.existsByLogin("jkowalski")).thenReturn(false);
        when(readerRepository.existsByEmail("j@k.pl")).thenReturn(false);

        service.createAccount(r);

        verify(readerRepository).saveReader(r);
        verify(eventPublisher).publish(any(AccountCreated.class));
    }

    @Test
    @DisplayName("createAccount with a taken login is rejected and nothing is saved")
    void createAccountDuplicate() {
        Reader r = reader();
        when(readerRepository.existsByLogin("jkowalski")).thenReturn(true);
        when(readerRepository.existsByEmail("j@k.pl")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.createAccount(r));

        verify(readerRepository, never()).saveReader(any());
        verify(eventPublisher, never()).publish(any(AccountEvent.class));
    }

    @Test
    @DisplayName("deleteAccount publishes AccountDeleted when there are no loans or pending payments")
    void deleteAccountHappyPath() {
        Reader r = reader();
        when(loanPort.hasActiveLoans(5)).thenReturn(false);
        when(paymentsPort.hasPendingPayments(5)).thenReturn(false);

        service.deleteAccount(r);

        verify(eventPublisher).publish(any(AccountDeleted.class));
    }

    @Test
    @DisplayName("deleteAccount is rejected when the reader still has active loans")
    void deleteAccountBlockedByLoans() {
        Reader r = reader();
        when(loanPort.hasActiveLoans(5)).thenReturn(true);
        when(paymentsPort.hasPendingPayments(5)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.deleteAccount(r));

        verify(eventPublisher, never()).publish(any(AccountEvent.class));
    }
}
