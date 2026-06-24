package Catalog.infrastructure.in;

import Catalog.application.ports.in.BookAvailabilityDto;
import Catalog.application.ports.in.ICatalogBrowsePort;

import java.util.List;

public class UIBrowseAdapter {

    private final ICatalogBrowsePort catalogBrowsePort;

    public UIBrowseAdapter(ICatalogBrowsePort catalogBrowsePort) {
        this.catalogBrowsePort = catalogBrowsePort;
    }

    public List<BookAvailabilityDto> search(String query) {
        return catalogBrowsePort.search(query);
    }
}
