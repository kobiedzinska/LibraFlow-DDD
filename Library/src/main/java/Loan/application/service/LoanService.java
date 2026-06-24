package Loan.application.service;


import Loan.application.domain.model.*;
import Loan.application.port.in.*;
import Loan.application.port.out.*;
import Loan.application.domain.service.*;

import java.time.LocalDateTime;

public class LoanService implements IManageLoanUseCase {

	private ILoanRepository loanRepository;
	private IDomainEventPublisher eventPublisher;
	private LoanCopyPolicy loanPolicy;
	private IPaymentPort paymentPort;
	private IReaderSnapshotAdapter readerSnapshotAdapter;
	private ICopyStatusAdapter copyStatusAdapter;

	public LoanService(ILoanRepository loanRepository, IDomainEventPublisher eventPublisher, LoanCopyPolicy loanPolicy, IPaymentPort readerRepository, IReaderSnapshotAdapter readerSnapshotAdapter, ICopyStatusAdapter copyStatusAdapter) {
		this.loanRepository = loanRepository;
		this.eventPublisher = eventPublisher;
		this.loanPolicy = loanPolicy;
		this.paymentPort = readerRepository;
		this.readerSnapshotAdapter = readerSnapshotAdapter;
		this.copyStatusAdapter = copyStatusAdapter;
	}

	/**
	 * 
	 * @param copyId
	 * @param readerId
	 */
	public void loanCopy(Integer copyId, Integer readerId) {
		System.out.println("Service loanCopy");
		LocalDateTime now = LocalDateTime.now();

		ReaderSnapshot reader =
				readerSnapshotAdapter.getReaderSnapshot(readerId);

		CopyAvailability availability =
				copyStatusAdapter.getCopyStatus(copyId);
System.out.println(loanPolicy.canBorrow(reader, availability));
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


		loan.returnCopy();

		if(loan.getDueDate().isBefore(loan.getReturnedAt())){
			eventPublisher.publish(
					new CopyOverdue(
							loan.getCopyId(),
							loan.getReaderId(),
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