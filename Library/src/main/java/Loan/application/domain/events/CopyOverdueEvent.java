package Loan.application.domain.events;

import java.time.LocalDateTime;

public class CopyOverdueEvent {
    private final int copyId;
    private final int readerId;
    private final LocalDateTime occurredOn;

    public CopyOverdueEvent(int copyId, int readerId, LocalDateTime occurredOn) {
        this.copyId = copyId;
        this.readerId = readerId;
        this.occurredOn = occurredOn;
    }

    public int getCopyId() { return copyId; }
    public int getReaderId() { return readerId; }
    public LocalDateTime getOccurredOn() { return occurredOn; }
}
