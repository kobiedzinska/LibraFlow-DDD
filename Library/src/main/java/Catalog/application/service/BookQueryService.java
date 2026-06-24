package Catalog.application.service;

import Catalog.application.domain.model.Book;
import Catalog.application.domain.model.Copy;
import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.in.BookAvailabilityDto;
import Catalog.application.ports.in.ICatalogBrowsePort;
import Catalog.application.ports.out.IBookRepository;
import Catalog.application.ports.out.ICopyRepository;

import java.util.List;

public class BookQueryService implements ICatalogBrowsePort {

    private final IBookRepository bookRepository;
    private final ICopyRepository copyRepository;

    public BookQueryService(IBookRepository bookRepository,
                            ICopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    public List<BookAvailabilityDto> search(String query) {

        List<Book> books = bookRepository.search(query);

        return books.stream()
                .map(book -> {

                    List<Copy> copies =
                            copyRepository.findByBookId(book.getBookId());

                    long available = copies.stream()
                            .filter(c -> c.getCopyStatus() == CopyStatus.AVAILABLE)
                            .count();

                    return new BookAvailabilityDto(
                            book.getBookId(),
                            book.getTitle(),
                            book.getAuthor(),
                            available
                    );
                })
                .toList();
    }
}
