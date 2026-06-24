package Loan.application.service;


import Loan.application.domain.model.*;
import Loan.application.port.in.*;
import Loan.application.port.out.*;
import Loan.application.domain.service.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class LoanService implements IManageLoanUseCase {

	private ILoanRepository loanRepository;
	private IDomainEventPublisher eventPublisher;
	private LoanCopyPolicy loanPolicy;
	private IPaymentPort paymentPort;
	private IReaderSnapshotPort readerSnapshotAdapter;
	private ICopyStatusPort copyStatusAdapter;

	public LoanService(ILoanRepository loanRepository, IDomainEventPublisher eventPublisher, LoanCopyPolicy loanPolicy, IPaymentPort readerRepository, IReaderSnapshotPort readerSnapshotAdapter, ICopyStatusPort copyStatusAdapter) {
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
	public void loanCopy(int copyId, int readerId) {
		LocalDateTime now = LocalDateTime.now();
		CopyAvailability availability = copyStatusAdapter.getCopyStatus(copyId);


		ReaderSnapshot reader = readerSnapshotAdapter.getReaderSnapshot(readerId);
        int activeLoans = 0;
        try {
            activeLoans = loanRepository.countActiveLoansByReaderId(readerId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reader.setActiveLoansCount(activeLoans);

		if (!loanPolicy.canBorrow(reader, availability))return;


		Loan loan = new Loan(
				-1,
				readerId,
				copyId,
				now.plusDays(30),
				now,
				LoanStatus.BORROWED,
				null
		);

		loanRepository.saveLoan(loan);
		System.out.println("Went");
		eventPublisher.publish(
				new CopyBorrowed(copyId, readerId, now)
		);
	}



	/**
	 * 
	 * @param loanId
	 */
	public void returnLoan(int loanId) {
		LocalDateTime now = LocalDateTime.now();
		Loan loan = loanRepository.findLoan(loanId);

		loan.returnCopy();
		loanRepository.saveLoan(loan);

		eventPublisher.publish(
				new CopyReturned(
						loan.getCopyId(),
						now
				)
		);

		if(loanPolicy.isOverdue(loan)){
			eventPublisher.publish(
				new LoanReturnedLate(loanId, loan.getReaderId(), loan.getDueDate(), loan.getReturnedAt())
			);
		}
	}

}