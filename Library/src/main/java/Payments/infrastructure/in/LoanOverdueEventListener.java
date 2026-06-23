package Payments.infrastructure.in;

import Payments.application.ports.in.ILoanOverdueEventListener;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanOverdueEventListener {

	private final ILoanOverdueEventListener fineCalculationService;

	public LoanOverdueEventListener(ILoanOverdueEventListener fineCalculationService) {
		this.fineCalculationService = fineCalculationService;
	}

	public void handleLoanOverdue(String json) {
		int loanId    = parseInt(json, "loanId");
		int clientId  = parseInt(json, "clientId");
		LocalDateTime dueDate    = parseDate(json, "dueDate");
		LocalDateTime returnDate = parseDate(json, "returnDate");

		fineCalculationService.handleLoanOverdue(dueDate, returnDate, clientId, loanId);
	}



	///////////////////////////////////////////////////////////////////////////
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