package Payments.infrastructure.in;

import Loan.application.domain.model.LoanOverdue;
import Payments.application.ports.in.ILoanOverdueEventListener;
import SharedKernel.EventHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoanOverdueHandler implements EventHandler<LoanOverdue> {
    private final ILoanOverdueEventListener fineCalculationService;

    public LoanOverdueHandler(ILoanOverdueEventListener fineCalculationService) {
        this.fineCalculationService = fineCalculationService;
    }

    @Override
    public void handle(LoanOverdue event) {
        fineCalculationService.handleLoanOverdue(event.getDueDate(), event.getReturnedAt(), event.getReaderId(), event.getLoanId());
    }

    private int parseInt(String json, String key) {
        String raw = extract(json, key);
        return raw == null ? 0 : Integer.parseInt(raw.trim());
    }

    private LocalDateTime parseDate(String json, String key) {
        String raw = extract(json, key);
        if (raw == null || raw.equals("null") || raw.isBlank()) {
            return null;
        }
        // format "2026-07-15" -> początek dnia
        return LocalDate.parse(raw, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
    }

    // wyciąga wartość spod klucza z prostego, płaskiego JSON-a
    private String extract(String json, String key) {
        String marker = "\"" + key + "\"";
        int k = json.indexOf(marker);
        if (k < 0) return null;

        int colon = json.indexOf(':', k + marker.length());
        if (colon < 0) return null;

        int i = colon + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;

        if (i < json.length() && json.charAt(i) == '"') {        // wartość w cudzysłowach
            int end = json.indexOf('"', i + 1);
            return json.substring(i + 1, end);
        } else {                                                  // liczba lub null
            int end = i;
            while (end < json.length()
                    && json.charAt(end) != ','
                    && json.charAt(end) != '}'
                    && !Character.isWhitespace(json.charAt(end))) {
                end++;
            }
            return json.substring(i, end);
        }
    }
}
