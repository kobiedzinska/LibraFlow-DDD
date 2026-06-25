package Catalog.application.ports.in;

public interface ICatalogPort {
    CopyStatusDto getCopyStatus(int copyId);
}

