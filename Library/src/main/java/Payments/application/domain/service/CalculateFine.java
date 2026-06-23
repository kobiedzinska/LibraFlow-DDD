package Payments.application.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalculateFine {

	/**
	 * 
	 * @param dueDate
	 * @param returnDate
	 */
	public double calculate(LocalDateTime dueDate, LocalDateTime returnDate) {

		if (returnDate == null) {
			returnDate = LocalDateTime.now();
		}

		long daysLate = ChronoUnit.DAYS.between(
				dueDate.toLocalDate(),
				returnDate.toLocalDate()
		);

		if (daysLate <= 0) {
			return 0.0;
		}

		double finePerDay = 1;
		return daysLate * finePerDay;
	}

}