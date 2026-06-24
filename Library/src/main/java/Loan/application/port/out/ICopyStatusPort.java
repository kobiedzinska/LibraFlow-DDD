package Loan.application.port.out;

import Loan.application.domain.model.*;

public interface ICopyStatusPort {

	/**
	 * 
	 * @param copyId
	 */
	CopyAvailability getCopyStatus(int copyId);

}