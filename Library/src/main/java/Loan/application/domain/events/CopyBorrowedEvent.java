package Loan.application.domain.events;

import java.time.LocalDateTime;

public class CopyBorrowedEvent {

    private final Integer copyId;
    private final Integer readerId;
    private final LocalDateTime occurredOn;

    public CopyBorrowedEvent(Integer copyId, Integer readerId, LocalDateTime occurredOn) {
        this.copyId = copyId;
        this.readerId = readerId;
        this.occurredOn = occurredOn;
    }

    public Integer getCopyId() { return copyId; }
    public Integer getReaderId() { return readerId; }
    public LocalDateTime getOccurredOn() { return occurredOn; }
}