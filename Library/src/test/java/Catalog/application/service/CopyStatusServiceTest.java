package Catalog.application.service;

import Catalog.application.domain.model.Copy;
import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.out.ICopyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CopyStatusService use case (Catalog Context) — reacts to Lending events")
class CopyStatusServiceTest {

    @Mock ICopyRepository copyRepository;
    private CopyStatusService service;

    @BeforeEach
    void setUp() {
        service = new CopyStatusService(copyRepository);
    }

    private Copy copy() {
        return new Copy(100, 10, CopyStatus.AVAILABLE);
    }

    @Test
    @DisplayName("handleCopyBorrowed() marks the copy borrowed and saves it")
    void handleCopyBorrowed() {
        Copy c = copy();
        when(copyRepository.findCopy(100)).thenReturn(c);

        service.handleCopyBorrowed(100);

        assertEquals(CopyStatus.BORROWED, c.getCopyStatus());
        verify(copyRepository).saveCopy(c);
    }

    @Test
    @DisplayName("handleCopyReturned() marks the copy available and saves it")
    void handleCopyReturned() {
        Copy c = copy();
        when(copyRepository.findCopy(100)).thenReturn(c);

        service.handleCopyReturned(100);

        assertEquals(CopyStatus.AVAILABLE, c.getCopyStatus());
        verify(copyRepository).saveCopy(c);
    }

    @Test
    @DisplayName("handleCopyOverdue() marks the copy overdue and saves it")
    void handleCopyOverdue() {
        Copy c = copy();
        when(copyRepository.findCopy(100)).thenReturn(c);

        service.handleCopyOverdue(100);

        assertEquals(CopyStatus.OVERDUE, c.getCopyStatus());
        verify(copyRepository).saveCopy(c);
    }

    @Test
    @DisplayName("an unknown copy raises and saves nothing")
    void unknownCopyThrows() {
        when(copyRepository.findCopy(999)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.handleCopyBorrowed(999));
        verify(copyRepository, never()).saveCopy(any());
    }
}
