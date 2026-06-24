package ReaderAccounts.application.ports.in;

public interface IAccountReaderPort {
    ReaderStatusDto getReaderStatus(int readerId);
}


