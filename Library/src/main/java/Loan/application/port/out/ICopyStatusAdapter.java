package Loan.application.port.out;

import Loan.application.domain.model.*;

public interface ICopyStatusAdapter {

	/**
	 * 
	 * @param copyId
	 */
	CopyAvailability getCopyStatus(Integer copyId);

}