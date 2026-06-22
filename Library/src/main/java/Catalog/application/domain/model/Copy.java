package Catalog.application.domain.model;

public class Copy {

	private Integer copyId;
	private Integer bookId;
	private CopyStatus copyStatus;


	public void markAvailable() {
		copyStatus=CopyStatus.AVAILABLE;
	}
	public void markBorrowed() {
		copyStatus=CopyStatus.BORROWED;
	}
	public void markOverdue() {
		copyStatus=CopyStatus.OVERDUE;
	}

}