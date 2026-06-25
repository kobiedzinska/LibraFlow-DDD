package Catalog.application.service;

import Catalog.application.domain.model.Book;
import Catalog.application.domain.model.Copy;
import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.in.BookAvailabilityDto;
import Catalog.application.ports.out.IBookRepository;
import Catalog.application.ports.out.ICopyRepository;
import Catalog.infrastructure.out.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * "Browse available books" use case: search books and report how many copies of
 * each are currently AVAILABLE.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BookQueryService use case (Catalog Context) — available book browsing")
class BookQueryServiceTest {

    @Mock IBookRepository bookRepository;
    @Mock ICopyRepository copyRepository;
    private BookRepository service;

    @BeforeEach
    void setUp() {
        service = new BookRepository(bookRepository, copyRepository);
    }

    private Book book() {
        return new Book(1, "Domain-Driven Design", "Evans",
                "isbn", "publisher", "2003", List.of("software"), "desc");
    }

    @Test
    @DisplayName("counts only AVAILABLE copies for each matching book")
    void countsAvailableCopies() {
        when(bookRepository.search("ddd")).thenReturn(List.of(book()));
        when(copyRepository.findByBookId(1)).thenReturn(List.of(
                new Copy(10, 1, CopyStatus.AVAILABLE),
                new Copy(11, 1, CopyStatus.AVAILABLE),
                new Copy(12, 1, CopyStatus.BORROWED),
                new Copy(13, 1, CopyStatus.OVERDUE)
        ));

        List<BookAvailabilityDto> result = service.search("ddd");

        assertEquals(1, result.size());
        BookAvailabilityDto dto = result.get(0);
        assertAll(
                () -> assertEquals(1, dto.getBookId()),
                () -> assertEquals("Domain-Driven Design", dto.getTitle()),
                () -> assertEquals("Evans", dto.getAuthor()),
                () -> assertEquals(2L, dto.getAvailableCopies())
        );
    }

    @Test
    @DisplayName("a book with no copies reports zero availability")
    void noCopiesMeansZero() {
        when(bookRepository.search("ddd")).thenReturn(List.of(book()));
        when(copyRepository.findByBookId(1)).thenReturn(List.of());

        List<BookAvailabilityDto> result = service.search("ddd");

        assertEquals(0L, result.get(0).getAvailableCopies());
    }

    @Test
    @DisplayName("no matching books yields an empty result")
    void noBooks() {
        when(bookRepository.search("zzz")).thenReturn(List.of());

        assertTrue(service.search("zzz").isEmpty());
    }
}
