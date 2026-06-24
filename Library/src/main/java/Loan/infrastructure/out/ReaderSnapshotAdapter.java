package Loan.infrastructure.out;

import Loan.application.domain.model.ReaderSnapshot;
import Loan.application.port.out.IReaderSnapshotAdapter;

public class ReaderSnapshotAdapter implements IReaderSnapshotAdapter {
    @Override
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
    }
}
