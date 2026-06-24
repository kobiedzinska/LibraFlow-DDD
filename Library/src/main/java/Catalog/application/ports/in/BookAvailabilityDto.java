package Catalog.application.ports.in;


public class BookAvailabilityDto {

    private int bookId;
    private String title;
    private String author;
    private long availableCopies;

    public BookAvailabilityDto(int bookId, String title, String author, long availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public long getAvailableCopies() {
        return availableCopies;
    }


    @Override
    public String toString() {
        return "BookAvailabilityDto{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", availableCopies=" + availableCopies +
                '}';
    }
}
