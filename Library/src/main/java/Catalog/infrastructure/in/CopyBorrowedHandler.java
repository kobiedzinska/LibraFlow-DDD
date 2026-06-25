package Catalog.infrastructure.in;

import Catalog.application.ports.in.ICopyStatusEventListener;
import Loan.application.domain.model.CopyBorrowed;
import SharedKernel.EventHandler;

public class CopyBorrowedHandler implements EventHandler<CopyBorrowed> {
    public ICopyStatusEventListener copyStatusService;

    public CopyBorrowedHandler(
            ICopyStatusEventListener copyStatusService) {
        this.copyStatusService = copyStatusService;
    }

    @Override
    public void handle(CopyBorrowed event) {
        copyStatusService.handleCopyBorrowed(
                event.getCopyId()
        );
    }
}
