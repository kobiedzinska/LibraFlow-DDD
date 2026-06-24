package Loan.application.port.out;

import Loan.application.domain.model.ReaderSnapshot;

public interface IReaderSnapshotAdapter {
    ReaderSnapshot getReaderSnapshot(Integer readerId);
}
