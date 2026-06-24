package Catalog.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Copy aggregate (Catalog Context / Supporting Domain)")
class CopyTest {

    private Copy copy() {
        return new Copy(1, 10, CopyStatus.AVAILABLE);
    }

    @Test
    @DisplayName("markBorrowed() sets status BORROWED")
    void markBorrowed() {
        Copy c = copy();
        c.markBorrowed();
        assertEquals(CopyStatus.BORROWED, c.getCopyStatus());
    }

    @Test
    @DisplayName("markOverdue() sets status OVERDUE")
    void markOverdue() {
        Copy c = copy();
        c.markOverdue();
        assertEquals(CopyStatus.OVERDUE, c.getCopyStatus());
    }

    @Test
    @DisplayName("markAvailable() restores AVAILABLE (PU6)")
    void markAvailable() {
        Copy c = copy();
        c.markBorrowed();
        c.markAvailable();
        assertEquals(CopyStatus.AVAILABLE, c.getCopyStatus());
    }

    @Test
    @DisplayName("keeps the link to its owning book")
    void retainsBookId() {
        Copy c = new Copy(5, 99, CopyStatus.AVAILABLE);
        assertAll(
                () -> assertEquals(5, c.getCopyId()),
                () -> assertEquals(99, c.getBookId())
        );
    }
}
