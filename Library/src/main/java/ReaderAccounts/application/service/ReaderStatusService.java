package ReaderAccounts.application.service;

import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.ports.in.*;
import ReaderAccounts.application.ports.out.*;

public class ReaderStatusService implements IReaderStatusEventListener {

	IReaderRepository readerRepository;
	ILoanPort copyPort;
	IPaymentsPort paymentsPort;


	public void handlePaymentOverdue(int readerId) {
		Reader reader = readerRepository.findUser(readerId);
		if (reader == null) {
			reader.block();
			readerRepository.saveReader(reader);
		}else {
			throw new IllegalArgumentException("reader does not exist");
		}
	}


	public void handlePaymentCompleted(int readerId) {
		Reader reader = readerRepository.findUser(readerId);
		if (reader == null) {
			reader.unblock();
			readerRepository.saveReader(reader);
		}else {
			throw new IllegalArgumentException("reader does not exist");
		}
	}

}