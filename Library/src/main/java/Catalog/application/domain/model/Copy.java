package Catalog.application.domain.model;

public class Copy {

	private int copyId;
	private int bookId;
	private CopyStatus copyStatus;


	public Copy(int copyId, int bookId, CopyStatus copyStatus) {
		this.copyId = copyId;
		this.bookId = bookId;
		this.copyStatus = copyStatus;
	}

	public void markAvailable() {
		copyStatus=CopyStatus.AVAILABLE;
	}
	public void markBorrowed() {
		copyStatus=CopyStatus.BORROWED;
	}
	public void markOverdue() {
		copyStatus=CopyStatus.OVERDUE;
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public CopyStatus getCopyStatus() {
		return copyStatus;
	}

	public void setCopyStatus(CopyStatus copyStatus) {
		this.copyStatus = copyStatus;
	}
}