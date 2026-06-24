package Catalog.infrastructure.in;

import Catalog.application.ports.in.ICopyStatusEventListener;
import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyOverdue;
import SharedKernel.EventHandler;

public class CopyOverdueHandler
        implements EventHandler<CopyOverdue> {

    private final ICopyStatusEventListener copyStatusService;

    public CopyOverdueHandler(
            ICopyStatusEventListener copyStatusService) {
        this.copyStatusService = copyStatusService;
    }

    @Override
    public void handle(CopyOverdue event) {
        copyStatusService.handleCopyOverdue(
                event.getCopyId()
        );
    }
}
