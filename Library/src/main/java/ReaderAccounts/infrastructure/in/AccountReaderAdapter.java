package ReaderAccounts.infrastructure.in;

import ReaderAccounts.application.ports.in.IAccountReaderPort;
import ReaderAccounts.application.ports.in.ReaderStatusDto;

public class AccountReaderAdapter  {

    IAccountReaderPort accountReaderPort;

    public AccountReaderAdapter(IAccountReaderPort accountReaderPort) {
        this.accountReaderPort = accountReaderPort;
    }
}
