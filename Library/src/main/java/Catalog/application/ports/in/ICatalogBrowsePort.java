package Catalog.application.ports.in;

import java.util.List;

public interface ICatalogBrowsePort {
    List<BookAvailabilityDto> search(String query);
}
