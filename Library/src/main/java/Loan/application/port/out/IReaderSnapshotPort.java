package Loan.application.port.out;

import Loan.application.domain.model.ReaderSnapshot;

public interface IReaderSnapshotPort {
    ReaderSnapshot getReaderSnapshot(int readerId);
}
