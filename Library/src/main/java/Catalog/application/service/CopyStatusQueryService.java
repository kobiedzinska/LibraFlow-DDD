package Catalog.application.service;

import Catalog.application.domain.model.Copy;
import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.in.CopyStatusDto;
import Catalog.application.ports.in.ICatalogPort;
import Catalog.application.ports.out.ICopyRepository;

public class CopyStatusQueryService implements ICatalogPort {
    private ICopyRepository copyRepository;

    public CopyStatusQueryService(ICopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public CopyStatusDto getCopyStatus(int copyId) {
        Copy copy = findCopyOrThrow(copyId);
        return new CopyStatusDto(copy.getCopyId(), toPublicStatus(copy.getCopyStatus()));
    }

    private CopyStatus toPublicStatus(CopyStatus internalStatus) {
        return switch (internalStatus) {
            case AVAILABLE -> CopyStatus.AVAILABLE;
            case BORROWED -> CopyStatus.BORROWED;
            case  OVERDUE -> CopyStatus.OVERDUE;
        };
    }

    private Copy findCopyOrThrow(int copyId) {
        Copy copy = copyRepository.findCopy(copyId);
        if (copy == null) {
            throw new IllegalArgumentException("Copy not found: " + copyId);
        }
        return copy;
    }
}
