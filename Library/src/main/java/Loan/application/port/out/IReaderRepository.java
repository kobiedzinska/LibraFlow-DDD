package Loan.application.port.out;

import Loan.application.domain.model.*;

public interface IReaderRepository {

	/**
	 * 
	 * @param readerId
	 */
	ReaderSnapshot getReaderSnapshot(Integer readerId);

}