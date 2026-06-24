package Loan.infrastructure.out;

import Loan.application.port.out.*;
import Loan.application.domain.model.*;

public class CopyStatusAdapter implements ICopyStatusAdapter {

	/**
	 * 
	 * @param copyId
	 */
	public CopyAvailability getCopyStatus(Integer copyId) {
		return CopyAvailability.AVAILABLE;
	}

}