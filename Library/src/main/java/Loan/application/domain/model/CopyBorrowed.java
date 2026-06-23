package Loan.application.domain.model;

import java.time.LocalDateTime;

public class CopyBorrowed extends CopyEvent{

    private final int readerId;
    public CopyBorrowed(int copyId, int readerId, LocalDateTime occurredOn) {
        super(copyId, occurredOn);
        this.readerId=readerId;
    }

    public int getReaderId() {
        return readerId;
    }
}
