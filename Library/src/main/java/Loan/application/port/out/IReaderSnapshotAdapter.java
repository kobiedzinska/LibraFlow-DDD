package Loan.application.port.out;

import Loan.application.domain.model.ReaderSnapshot;

public interface IReaderSnapshotAdapter {
    public ReaderSnapshot getReaderSnapshot(Integer readerId);
}
