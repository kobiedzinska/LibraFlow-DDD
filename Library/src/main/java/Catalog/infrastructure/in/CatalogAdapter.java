package Catalog.infrastructure.in;

import Catalog.application.ports.in.CopyStatusDto;
import Catalog.application.ports.in.ICatalogPort;

public class CatalogAdapter{

    public ICatalogPort catalogPort;

    public CatalogAdapter(ICatalogPort catalogPort) {
        this.catalogPort = catalogPort;
    }
}
