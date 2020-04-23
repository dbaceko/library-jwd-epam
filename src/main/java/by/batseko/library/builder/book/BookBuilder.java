package by.batseko.library.builder.book;

import by.batseko.library.entity.book.*;

public class BookBuilder {
    private String uuid;
    private Genre genre;
    private BookLanguage bookLanguage;
    private Publisher publisher;
    private Author author;
    private String title;
    private int publishYear;
    private int pagesQuantity;
    private String description;
    private int availableBookQuantity;

    public String getUuid() {
        return uuid;
    }

    public BookBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public BookBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public BookLanguage getBookLanguage() {
        return bookLanguage;
    }

    public BookBuilder setBookLanguage(BookLanguage bookLanguage) {
        this.bookLanguage = bookLanguage;
        return this;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public BookBuilder setPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public BookBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public BookBuilder setPublishYear(int publishYear) {
        this.publishYear = publishYear;
        return this;
    }

    public int getPagesQuantity() {
        return pagesQuantity;
    }

    public BookBuilder setPagesQuantity(int pagesQuantity) {
        this.pagesQuantity = pagesQuantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getAvailableBookQuantity() {
        return availableBookQuantity;
    }

    public BookBuilder setAvailableBookQuantity(int availableBookQuantity) {
        this.availableBookQuantity = availableBookQuantity;
        return this;
    }

    public Book build() {
        return new Book(this);
    }
}
