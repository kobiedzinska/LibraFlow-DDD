package ReaderAccounts.application.ports.out;

import ReaderAccounts.application.domain.model.AccountEvent;

public interface ICatalogEventPublisher {

	void publish(AccountEvent event);

	void subscribe();

	void reset();

}