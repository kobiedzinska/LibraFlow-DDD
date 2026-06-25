package Payments.application.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculateFine domain service (Payment Context) — PU2 fine policy")
class CalculateFineTest {

    private final CalculateFine calculator = new CalculateFine();
    private static final LocalDateTime DUE = LocalDateTime.of(2026, 1, 31, 12, 0);

    @Test
    @DisplayName("no fine when returned on the due date")
    void noFineOnTime() {
        assertEquals(0.0, calculator.calculate(DUE, DUE));
    }

    @Test
    @DisplayName("no fine when returned before the due date")
    void noFineEarly() {
        assertEquals(0.0, calculator.calculate(DUE, DUE.minusDays(2)));
    }

    @ParameterizedTest(name = "{0} day(s) late -> fine {1}")
    @CsvSource({"1, 1.0", "2, 2.0", "10, 10.0"})
    @DisplayName("charges 1.0 per full overdue day (date-based)")
    void chargesPerDay(int daysLate, double expectedFine) {
        assertEquals(expectedFine, calculator.calculate(DUE, DUE.plusDays(daysLate)), 1e-9);
    }

    @Test
    @DisplayName("a same-day-later return (only hours late) is not yet a full day, so no fine")
    void partialDayIsNotCharged() {
        // policy truncates to LocalDate, so +1h on the same calendar day = 0 days late
        assertEquals(0.0, calculator.calculate(DUE, DUE.plusHours(1)), 1e-9);
    }

    @Test
    @DisplayName("a null return date is treated as now (so a past due date yields a fine)")
    void nullReturnUsesNow() {
        LocalDateTime longAgo = LocalDateTime.now().minusDays(5);
        assertTrue(calculator.calculate(longAgo, null) >= 4.0);
    }
}
