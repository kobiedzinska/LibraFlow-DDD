package Loan.application.service;


import Loan.application.domain.model.*;
import Loan.application.port.in.*;
import Loan.application.port.out.*;
import Loan.application.domain.service.*;

import java.time.LocalDateTime;

public class LoanService implements ILoanManageLoanUseCase {

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
		LocalDateTime now = LocalDateTime.now();

		ReaderSnapshot reader =
				readerRepository.getReaderSnapshot(readerId);

		CopyAvailability availability =
				copyStatusAdapter.getCopyStatus(copyId);

		if (!loanPolicy.canBorrow(reader, availability))return;


		Loan loan = new Loan(
				null,
				readerId,
				copyId,
				now.plusDays(30),
				now,
				LoanStatus.BORROWED,
				null
		);

		loanRepository.saveLoan(loan);

		eventPublisher.publish(
				new CopyBorrowed(copyId, readerId, now)
		);
	}



	/**
	 * 
	 * @param loanId
	 */
	public void returnLoan(Integer loanId) {
		LocalDateTime now = LocalDateTime.now();
		Loan loan = loanRepository.findLoan(loanId);
		ReaderSnapshot reader = readerRepository.getReaderSnapshot(loan.getReaderId());

		loan.returnCopy();

		if(loan.getDueDate().isBefore(loan.getReturnedAt())){
			reader.setBlocked(true);
			eventPublisher.publish(
					new CopyOverdue(
							loan.getCopyId(),
							now
					)
			);
		}else{
			eventPublisher.publish(
					new CopyReturned(
							loan.getCopyId(),
							now
					)
			);
		}

		loanRepository.saveLoan(loan);


	}

}