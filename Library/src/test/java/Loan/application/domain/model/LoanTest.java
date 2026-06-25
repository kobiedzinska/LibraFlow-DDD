package Loan.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Loan aggregate (Lending Context / Core Domain)")
class LoanTest {

    private static final LocalDateTime BORROWED = LocalDateTime.of(2026, 1, 1, 10, 0);
    private static final LocalDateTime DUE = BORROWED.plusDays(30);

    private Loan borrowedLoan() {
        return new Loan(1, 42, 100, DUE, BORROWED, LoanStatus.BORROWED, null);
    }

    @Nested
    @DisplayName("construction (PU1)")
    class Construction {

        @Test
        @DisplayName("keeps the values it is given")
        void keepsValues() {
            Loan loan = borrowedLoan();

            assertAll(
                    () -> assertEquals(1, loan.getLoanId()),
                    () -> assertEquals(42, loan.getReaderId()),
                    () -> assertEquals(100, loan.getCopyId()),
                    () -> assertEquals(DUE, loan.getDueDate()),
                    () -> assertEquals(BORROWED, loan.getBorrowedAt()),
                    () -> assertEquals(LoanStatus.BORROWED, loan.getLoanStatus()),
                    () -> assertNull(loan.getReturnedAt())
            );
        }
    }

    @Nested
    @DisplayName("borrow event (PU1)")
    class BorrowEvent {

        @Test
        @DisplayName("borrowEvent() carries the copy and reader ids")
        void borrowEventCarriesIds() {
            CopyBorrowed event = borrowedLoan().borrowEvent();

            assertAll(
                    () -> assertEquals(100, event.getCopyId()),
                    () -> assertEquals(42, event.getReaderId()),
                    () -> assertNotNull(event.getOccurredOn())
            );
        }
    }

    @Nested
    @DisplayName("returning (PU2)")
    class Returning {

        @Test
        @DisplayName("returnCopy() sets status RETURNED and records a return time")
        void returnSetsStatusAndTime() {
            Loan loan = borrowedLoan();

            loan.returnCopy();

            assertAll(
                    () -> assertEquals(LoanStatus.RETURNED, loan.getLoanStatus()),
                    () -> assertNotNull(loan.getReturnedAt())
            );
        }

        @Test
        @DisplayName("a copy returned after its due date counts as overdue")
        void overdueWhenReturnedLate() {
            // due date in the past, so the just-now return time is after it
            Loan loan = new Loan(1, 42, 100,
                    LocalDateTime.now().minusDays(1),
                    LocalDateTime.now().minusDays(31),
                    LoanStatus.BORROWED, null);

            loan.returnCopy();

            assertTrue(loan.isOverdue());
        }

        @Test
        @DisplayName("a copy returned before its due date is not overdue")
        void notOverdueWhenReturnedEarly() {
            Loan loan = new Loan(1, 42, 100,
                    LocalDateTime.now().plusDays(10),
                    LocalDateTime.now().minusDays(1),
                    LoanStatus.BORROWED, null);

            loan.returnCopy();

            assertFalse(loan.isOverdue());
        }
    }
}
