package Catalog.application.domain.model;

public class Copy {

	private Integer copyId;
	private Integer bookId;
	private CopyStatus copyStatus;


	public Copy(Integer copyId, Integer bookId, CopyStatus copyStatus) {
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

	public Integer getCopyId() {
		return copyId;
	}

	public void setCopyId(Integer copyId) {
		this.copyId = copyId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public CopyStatus getCopyStatus() {
		return copyStatus;
	}

	public void setCopyStatus(CopyStatus copyStatus) {
		this.copyStatus = copyStatus;
	}
}