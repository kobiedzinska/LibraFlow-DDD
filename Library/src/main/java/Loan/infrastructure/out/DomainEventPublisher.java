package Loan.infrastructure.out;

import Catalog.infrastructure.in.LoanEventListener;
import Loan.application.domain.model.CopyBorrowed;
import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;
import Loan.application.port.out.*;

import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisher implements IDomainEventPublisher {

	private List<Object> subscribers = new ArrayList<>();

	public void subscribe(Object handler) {
		subscribers.add(handler);
	}

	public void publish(Object event) {

		for (Object handler : subscribers) {

			if (handler instanceof LoanEventListener listener) {

				if (event instanceof CopyBorrowed e)
					listener.handleCopyBorrowed(e.getCopyId());

				if (event instanceof CopyReturned e)
					listener.handleCopyReturned(e.getCopyId());

				if (event instanceof CopyOverdue e)
					listener.handleCopyOverdue(e.getCopyId());
			}
		}
	}

}