package Loan.infrastructure.out.http;

import Loan.application.port.out.*;
import Loan.application.domain.model.*;

public class CopyStatusAdapter implements ICopyStatusAdapter {

	/**
	 * 
	 * @param copyId
	 */
	public CopyAvailability getCopyStatus(Integer copyId) {
		//db to connect TODO
		return null;
	}

}