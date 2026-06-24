package ReaderAccounts.application.service;

import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.ports.in.IAccountReaderPort;
import ReaderAccounts.application.ports.in.ReaderStatusDto;
import ReaderAccounts.application.ports.out.persistence.IReaderRepository;

public class ReaderStatusQueryService implements IAccountReaderPort {

    IReaderRepository readerRepository;

    public ReaderStatusQueryService(IReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }
    @Override
    public ReaderStatusDto getReaderStatus(int readerId) {


        Reader reader = readerRepository.findUser(readerId);

        if (reader == null) {
            throw new IllegalArgumentException("Reader not found");
        }

        return new ReaderStatusDto(
                reader.getReaderId(),
                reader.getBlocked()
        );
    }
}
