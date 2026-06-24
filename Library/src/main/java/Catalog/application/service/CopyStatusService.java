package Catalog.application.service;

import Catalog.application.domain.model.Copy;
import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.in.CopyStatusDto;
import Catalog.application.ports.in.*;
import Catalog.application.ports.out.*;



public class CopyStatusService implements ICopyStatusEventListener, ICatalogPort {

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

	@Override
	public CopyStatusDto getCopyStatus(Integer copyId) {
		Copy copy = findCopyOrThrow(copyId);
		return new CopyStatusDto(copy.getCopyId(), toPublicStatus(copy.getCopyStatus()));
	}

	private CopyStatus toPublicStatus(CopyStatus internalStatus) {
		return switch (internalStatus) {
			case AVAILABLE -> CopyStatus.AVAILABLE;
			case BORROWED -> CopyStatus.BORROWED;
			case  OVERDUE -> CopyStatus.OVERDUE;
		};
	}

	private Copy findCopyOrThrow(Integer copyId) {
		Copy copy = copyRepository.findCopy(copyId);
		if (copy == null) {
			throw new IllegalArgumentException("Copy not found: " + copyId);
		}
		return copy;
	}


}