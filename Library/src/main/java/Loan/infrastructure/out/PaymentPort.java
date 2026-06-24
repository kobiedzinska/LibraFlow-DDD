package Loan.infrastructure.out;

import Loan.application.port.out.*;
import Loan.application.domain.model.*;

public class PaymentPort implements IPaymentPort {
	boolean isPaid;

	/**
	 * 
	 * @param loanId
	 */
	@Override
	public boolean checkIsPaid(int loanId) {
		return isPaid;
	}
}