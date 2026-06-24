package Loan.application.domain.model;


import SharedKernel.DomainEvent;

import java.time.LocalDateTime;

public class LoanReturnedLate
        implements DomainEvent {

    private final int loanId;
    private final int readerId;
    private final LocalDateTime dueDate;
    private final LocalDateTime returnedAt;

    public LoanReturnedLate(int loanId, int readerId, LocalDateTime dueDate, LocalDateTime returnedAt) {
        this.loanId = loanId;
        this.readerId = readerId;
        this.dueDate = dueDate;
        this.returnedAt = returnedAt;
    }


    public int getLoanId() {
        return loanId;
    }

    public int getReaderId() {
        return readerId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }
}

