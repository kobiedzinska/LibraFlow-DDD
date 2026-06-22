package Loan.application.service;

import Loan.application.domain.events.CopyBorrowedEvent;
import Loan.application.domain.events.CopyReturnedEvent;
import Loan.application.domain.model.CopyAvailability;
import Loan.application.domain.model.LoanStatus;
import Loan.application.domain.model.ReaderSnapshot;
import Loan.application.port.in.*;
import Loan.application.port.out.*;
import Loan.application.domain.service.*;
import Loan.application.domain.model.Loan;

import java.time.LocalDateTime;

public class LoanService implements IManageLoanUseCase {

	private ILoanRepository loanRepository;
	private IDomainEventPublisher eventPublisher;
	private LoanCopyPolicy loanPolicy;
	private IReaderRepository readerRepository;
	private ICopyStatusAdapter copyStatusAdapter;

	/**
	 * 
	 * @param copyId
	 * @param readerId
	 */
	public void loanCopy(Integer copyId, Integer readerId) {

		ReaderSnapshot reader =
				readerRepository.getReaderSnapshot(readerId);

		CopyAvailability availability =
				copyStatusAdapter.getCopyStatus(copyId);

		loanPolicy.canBorrow(reader, availability);

		Loan loan = new Loan(
				null,
				readerId,
				copyId,
				LocalDateTime.now().plusDays(30),
				LocalDateTime.now(),
				LoanStatus.BORROWED,
				null
		);

		loanRepository.saveLoan(loan);

		eventPublisher.publish(
				new CopyBorrowedEvent(copyId, readerId, LocalDateTime.now())
		);
	}

	/*	public void loanCopy(int loanId, int readerId) {
		ReaderSnapshot reader =
				readerRepository.getReaderSnapshot(readerId);

		CopyAvailability availability =
				copyStatusAdapter.getCopyStatus(copyId);

		loanPolicy.canBorrow(reader, availability);

		Loan loan = Loan.create(readerId, copyId);

		loanRepository.saveLoan(loan);

		eventPublisher.publish(
				new CopyBorrowedEvent(copyId, readerId, now)
		);
	}*/


	/**
	 * 
	 * @param loanId
	 */
	public void returnLoan(Integer loanId) {
		Loan loan = loanRepository.findLoan(loanId);

		loan.returnCopy();

		loanRepository.saveLoan(loan);

		eventPublisher.publish(
				new CopyReturnedEvent(
						loan.getCopyId(),
						LocalDateTime.now()
				)
		);
	}

}