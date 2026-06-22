package Loan.application.domain.events;

import java.time.LocalDateTime;

public class CopyReturnedEvent {
    private final Integer copyId;
    private final LocalDateTime occurredOn;

    public CopyReturnedEvent(Integer copyId, LocalDateTime occurredOn) {
        this.copyId = copyId;
        this.occurredOn = occurredOn;
    }

    public Integer getCopyId() { return copyId; }
    public LocalDateTime getOccurredOn() { return occurredOn; }
}
