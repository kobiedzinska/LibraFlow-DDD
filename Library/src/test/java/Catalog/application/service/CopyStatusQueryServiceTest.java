package Catalog.application.service;

import Catalog.application.domain.model.Copy;
import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.in.CopyStatusDto;
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
@DisplayName("CopyStatusQueryService use case (Catalog Context) — single copy status")
class CopyStatusQueryServiceTest {

    @Mock ICopyRepository copyRepository;
    private CopyStatusQueryService service;

    @BeforeEach
    void setUp() {
        service = new CopyStatusQueryService(copyRepository);
    }

    @Test
    @DisplayName("returns a DTO carrying the copy id and its status")
    void returnsStatusDto() {
        when(copyRepository.findCopy(100))
                .thenReturn(new Copy(100, 10, CopyStatus.BORROWED));

        CopyStatusDto dto = service.getCopyStatus(100);

        assertAll(
                () -> assertEquals(100, dto.copyId()),
                () -> assertEquals(CopyStatus.BORROWED, dto.status())
        );
    }

    @Test
    @DisplayName("an unknown copy raises IllegalArgumentException")
    void unknownCopyThrows() {
        when(copyRepository.findCopy(999)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.getCopyStatus(999));
    }
}
