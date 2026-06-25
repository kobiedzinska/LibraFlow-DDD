package Loan.infrastructure.out;

import Loan.application.port.out.*;

public class PaymentAdapter implements IPaymentPort {
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