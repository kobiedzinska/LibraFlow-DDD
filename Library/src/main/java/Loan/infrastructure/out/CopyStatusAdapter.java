package Loan.infrastructure.out;

import Catalog.application.domain.model.CopyStatus;
import Catalog.application.ports.in.CopyStatusDto;
import Catalog.application.ports.in.ICatalogPort;
import Loan.application.port.out.*;
import Loan.application.domain.model.*;

public class CopyStatusAdapter implements ICopyStatusAdapter {

	/**
	 * 
	 * @param copyId
	 */


	private final ICatalogPort catalogCopyQuery;

	public CopyStatusAdapter(ICatalogPort catalogCopyQuery) {
		this.catalogCopyQuery = catalogCopyQuery;
	}

	@Override
	public CopyAvailability getCopyStatus(Integer copyId) {
		CopyStatusDto dto = catalogCopyQuery.getCopyStatus(copyId);
		return mapToAvailability(dto.status());
	}

	private CopyAvailability mapToAvailability(CopyStatus status) {
		if(status == CopyStatus.AVAILABLE){
			return CopyAvailability.AVAILABLE;
		}else{
			return CopyAvailability.BORROWED;
		}
	}

}