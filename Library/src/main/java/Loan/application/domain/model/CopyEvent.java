package Loan.application.domain.model;

import SharedKernel.DomainEvent;

import java.time.LocalDateTime;

public abstract class CopyEvent implements DomainEvent {
    private final int copyId;
    private final LocalDateTime occurredOn;

    protected CopyEvent(int copyId, LocalDateTime occurredOn) {
        this.copyId = copyId;
        this.occurredOn = occurredOn;
    }

    public int getCopyId() { return copyId; }
    public LocalDateTime getOccurredOn() { return occurredOn; }
}
