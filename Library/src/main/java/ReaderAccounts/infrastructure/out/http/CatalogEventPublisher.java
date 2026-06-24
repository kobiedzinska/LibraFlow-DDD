package ReaderAccounts.infrastructure.out.http;

import ReaderAccounts.application.domain.model.AccountEvent;
import ReaderAccounts.application.ports.out.http.ICatalogEventPublisher;

public class CatalogEventPublisher implements ICatalogEventPublisher {

	public void publish(AccountEvent event) {
/*		// TODO - implement DomainEventPublisherAdapter.publish
		throw new UnsupportedOperationException();*/
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void reset() {

	}

}