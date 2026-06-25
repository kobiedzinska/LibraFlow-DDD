package Catalog.infrastructure.in;

import Catalog.application.ports.in.ICopyStatusEventListener;
import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyReturned;
import SharedKernel.EventHandler;

public class CopyReturnedHandler
        implements EventHandler<CopyReturned> {

    private final ICopyStatusEventListener copyStatusService;

    public CopyReturnedHandler(
            ICopyStatusEventListener copyStatusService) {
        this.copyStatusService = copyStatusService;
    }

    @Override
    public void handle(CopyReturned event) {
        copyStatusService.handleCopyReturned(
                event.getCopyId()
        );
    }
}
