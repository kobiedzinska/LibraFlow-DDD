package ReaderAccounts.application.domain.service;

import ReaderAccounts.application.domain.model.*;

public class DeleteAccount {

	public Boolean checkNoLoans(boolean hasActiveLoans) {
		return !hasActiveLoans;
	}

	public Boolean checkisNoPayements(boolean hasPendingPayments) {
		return !hasPendingPayments;
	}

	// reguła zbiorcza: konto można usunąć tylko bez wypożyczeń i bez zaległości
	public boolean canDelete(boolean hasActiveLoans, boolean hasPendingPayments) {
		return checkNoLoans(hasActiveLoans) && checkisNoPayements(hasPendingPayments);
	}
}