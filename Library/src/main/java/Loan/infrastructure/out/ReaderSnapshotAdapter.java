package Loan.infrastructure.out;

import Loan.application.domain.model.ReaderSnapshot;
import Loan.application.port.out.IReaderSnapshotPort;
import ReaderAccounts.application.ports.in.IAccountReaderPort;
import ReaderAccounts.application.ports.in.ReaderStatusDto;

public class ReaderSnapshotAdapter implements IReaderSnapshotPort{
    /*@Override
    public ReaderSnapshot getReaderSnapshot(Integer readerId) {

        // TU w realnym systemie:
        // - HTTP call do Identity service
        // - albo DB read model
        // - albo event projection

        ReaderSnapshot snapshot = new ReaderSnapshot();
        snapshot.setReaderId(readerId);
        snapshot.setBlocked(false);
        snapshot.setActiveLoansCount(2);

        return snapshot;
    }*/
    private final IAccountReaderPort accountsReaderPort;

    public ReaderSnapshotAdapter(IAccountReaderPort accountsReaderPort) {
        this.accountsReaderPort = accountsReaderPort;
    }

    @Override
    public ReaderSnapshot getReaderSnapshot(int readerId) {
        //System.out.println("Im in reader adapter");
        ReaderStatusDto dto = accountsReaderPort.getReaderStatus(readerId);
        //System.out.println(dto);// typ Accounts - tylko tu
        return mapToSnapshot(dto);                                           // od razu tłumaczone na typ Loan
    }

    private ReaderSnapshot mapToSnapshot(ReaderStatusDto dto) {
        ReaderSnapshot snapshot = new ReaderSnapshot();
        snapshot.setReaderId(dto.readerId());
        snapshot.setBlocked(dto.isBlocked());
        return snapshot;
    }
}
