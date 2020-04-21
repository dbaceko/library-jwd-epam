package by.batseko.library.entity.book;

import by.batseko.library.builder.book.BookBuilder;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private String uuid;
    private Genre genre;
    private BookLanguage bookLanguage;
    private Publisher publisher;
    private Author author;
    private String title;
    private int publishYear;
    private int pagesQuantity;
    private String description;

    public Book(BookBuilder builder) {
        this.uuid = builder.getUuid();
        this.genre = builder.getGenre();
        this.bookLanguage = builder.getBookLanguage();
        this.publisher = builder.getPublisher();
        this.author = builder.getAuthor();
        this.title = builder.getTitle();
        this.publishYear = builder.getPublishYear();
        this.pagesQuantity = builder.getPagesQuantity();
        this.description = builder.getDescription();
    }

    public Book() {}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public BookLanguage getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(BookLanguage bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getPagesQuantity() {
        return pagesQuantity;
    }

    public void setPagesQuantity(int pagesQuantity) {
        this.pagesQuantity = pagesQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getPublishYear() == book.getPublishYear() &&
                getPagesQuantity() == book.getPagesQuantity() &&
                Objects.equals(getUuid(), book.getUuid()) &&
                Objects.equals(getGenre(), book.getGenre()) &&
                Objects.equals(getBookLanguage(), book.getBookLanguage()) &&
                Objects.equals(getPublisher(), book.getPublisher()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getDescription(), book.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getGenre(), getBookLanguage(), getPublisher(), getAuthor(), getTitle(), getPublishYear(), getPagesQuantity(), getDescription());
    }

    @Override
    public String toString() {
        return "Book{" +
                "uuid='" + uuid + '\'' +
                ", genre=" + genre +
                ", language=" + bookLanguage +
                ", publisher=" + publisher +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", publishYear=" + publishYear +
                ", pagesQuantity=" + pagesQuantity +
                ", description='" + description + '\'' +
                '}';
    }
}
