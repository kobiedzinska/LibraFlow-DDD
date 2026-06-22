package Loan.application.domain.service;

import Loan.application.domain.model.*;

public class LoanCopyPolicy {

	/**
	 * 
	 * @param reader
	 * @param availability
	 */
	public void canBorrow(ReaderSnapshot reader, CopyAvailability availability) {
		if (reader.getBlocked()) {
			throw new IllegalStateException("Reader is blocked");
		}

		if (reader.getActiveLoansCount() >= 5) {
			throw new IllegalStateException("Loan limit exceeded");
		}

		if (availability != CopyAvailability.AVAILABLE) {
			throw new IllegalStateException("Copy not available");
		}
	}

}