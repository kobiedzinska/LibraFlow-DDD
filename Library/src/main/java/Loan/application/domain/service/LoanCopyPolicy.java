package Loan.application.domain.service;

import Loan.application.domain.model.*;

public class LoanCopyPolicy {

	/**
	 * 
	 * @param reader
	 * @param availability
	 */
	public boolean canBorrow(ReaderSnapshot reader, CopyAvailability availability) {
		//throw new IllegalStateException("Reader is blocked");
		return !reader.getBlocked() && reader.getActiveLoansCount() < 5 && availability == CopyAvailability.AVAILABLE;
	}

}