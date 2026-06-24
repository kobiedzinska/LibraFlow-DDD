package Loan.application.port.out;

import Loan.application.domain.model.*;

public interface IPaymentPort {

	/**
	 * 
	 * @param loanId
	 */
	public boolean checkIsPaid(int loanId);

}