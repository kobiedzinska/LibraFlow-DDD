package Catalog.application.service;

import Catalog.application.domain.model.Copy;
import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.in.CopyStatusDto;
import Catalog.application.ports.in.*;
import Catalog.application.ports.out.*;



public class CopyStatusService implements ICopyStatusEventListener{

	private ICopyRepository copyRepository;


	public CopyStatusService(ICopyRepository copyRepository) {
		this.copyRepository = copyRepository;
	}

	/**
	 * 
	 * @param copyId
	 */
	public void handleCopyBorrowed(int copyId) {
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
	public void handleCopyReturned(int copyId) {

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
	public void handleCopyOverdue(int copyId) {

		Copy copy = copyRepository.findCopy(copyId);

		if (copy == null) {
			throw new IllegalArgumentException(
					"Copy not found: " + copyId);
		}

		copy.markOverdue();

		copyRepository.saveCopy(copy);
	}




}