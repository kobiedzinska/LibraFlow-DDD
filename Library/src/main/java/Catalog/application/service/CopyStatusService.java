package Catalog.application.service;

import Catalog.application.domain.model.Copy;
import Catalog.application.ports.in.*;
import Catalog.application.ports.out.*;
import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;


public class CopyStatusService implements ICopyStatusEventListener {

	private ICopyRepository copyRepository;


	public CopyStatusService(ICopyRepository copyRepository) {
		this.copyRepository = copyRepository;
	}

	/**
	 * 
	 * @param copyId
	 */
	public void handleCopyBorrowed(Integer copyId) {
		System.out.println("Catalog/copyStatusService: handleCopyBorrowed");
		Copy copy = copyRepository.findCopy(copyId);

		if (copy == null) {
			throw new IllegalArgumentException(
					"Copy not found: " + copyId);
		}

		copy.markBorrowed();

		copyRepository.saveCopy(copy);
	}

	/**
	 * 
	 * @param copyId
	 */
	public void handleCopyReturned(Integer copyId) {

		Copy copy = copyRepository.findCopy(copyId);

		if (copy == null) {
			throw new IllegalArgumentException(
					"Copy not found: " + copyId);
		}

		copy.markAvailable();

		copyRepository.saveCopy(copy);
	}

	/**
	 * 
	 * @param copyId
	 */
	@Override
	public void handleCopyOverdue(Integer copyId) {

		Copy copy = copyRepository.findCopy(copyId);

		if (copy == null) {
			throw new IllegalArgumentException(
					"Copy not found: " + copyId);
		}

		copy.markOverdue();

		copyRepository.saveCopy(copy);
	}

}