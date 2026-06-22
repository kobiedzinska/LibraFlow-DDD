package Loan.application.domain.events;

import java.time.LocalDateTime;

public class CopyReturnedEvent {
    private final int copyId;
    private final LocalDateTime occurredOn;

    public CopyReturnedEvent(int copyId, LocalDateTime occurredOn) {
        this.copyId = copyId;
        this.occurredOn = occurredOn;
    }

    public int getCopyId() { return copyId; }
    public LocalDateTime getOccurredOn() { return occurredOn; }
}
