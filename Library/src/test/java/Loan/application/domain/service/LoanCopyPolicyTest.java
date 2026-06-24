package Loan.application.domain.service;

import Loan.application.domain.model.CopyAvailability;
import Loan.application.domain.model.ReaderSnapshot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoanCopyPolicy domain service — PU1 borrowing rules")
class LoanCopyPolicyTest {

    private final LoanCopyPolicy policy = new LoanCopyPolicy();

    private ReaderSnapshot reader(boolean blocked, int activeLoans) {
        ReaderSnapshot r = new ReaderSnapshot();
        r.setReaderId(1);
        r.setBlocked(blocked);
        r.setActiveLoansCount(activeLoans);
        return r;
    }

    @Test
    @DisplayName("allows borrowing for an unblocked reader below the limit and an available copy")
    void allowsBorrowing() {
        assertTrue(policy.canBorrow(reader(false, 2), CopyAvailability.AVAILABLE));
    }

    @Test
    @DisplayName("rejects borrowing when the reader is blocked")
    void rejectsBlockedReader() {
        assertFalse(policy.canBorrow(reader(true, 0), CopyAvailability.AVAILABLE));
    }

    @Test
    @DisplayName("rejects borrowing when the reader reached the 5-book limit")
    void rejectsAtLimit() {
        assertFalse(policy.canBorrow(reader(false, 5), CopyAvailability.AVAILABLE));
    }

    @Test
    @DisplayName("allows borrowing the 5th book (limit is exclusive of the new loan)")
    void allowsJustBelowLimit() {
        assertTrue(policy.canBorrow(reader(false, 4), CopyAvailability.AVAILABLE));
    }

    @Test
    @DisplayName("rejects borrowing when the copy is already borrowed")
    void rejectsUnavailableCopy() {
        assertFalse(policy.canBorrow(reader(false, 0), CopyAvailability.BORROWED));
    }
}
