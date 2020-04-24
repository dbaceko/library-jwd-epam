package by.batseko.library.builder.book;

import by.batseko.library.entity.book.*;
import by.batseko.library.entity.book.bookcomponent.*;

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

    public BookBuilder setGenreUUID(String uuid) {
        defineGenreIfNull();
        genre.setUuid(uuid);
        return this;
    }

    public BookBuilder setGenreTitle(String genre) {
        defineGenreIfNull();
        this.genre.setGenreTitle(genre);
        return this;
    }

    public BookLanguage getBookLanguage() {
        return bookLanguage;
    }

    public BookBuilder setBookLanguage(BookLanguage bookLanguage) {
        this.bookLanguage = bookLanguage;
        return this;
    }

    public BookBuilder setBookLanguageUUID(String uuid) {
        defineBookLanguageIfNull();
        bookLanguage.setUuid(uuid);
        return this;
    }

    public BookBuilder setBookLanguageTitle(String bookLanguage) {
        defineBookLanguageIfNull();
        this.bookLanguage.setLanguageTitle(bookLanguage);
        return this;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public BookBuilder setPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder setPublisherUUID(String uuid) {
        definePublisherIfNull();
        publisher.setUuid(uuid);
        return this;
    }

    public BookBuilder setPublisherTitle(String publisher) {
        definePublisherIfNull();
        this.publisher.setPublisherTitle(publisher);
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public BookBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookBuilder setAuthorUUID(String uuid) {
        defineAuthorIfNull();
        publisher.setUuid(uuid);
        return this;
    }

    public BookBuilder setAuthorName(String publisher) {
        defineAuthorIfNull();
        this.publisher.setPublisherTitle(publisher);
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

    private void defineGenreIfNull() {
        if (genre == null) {
            genre = new Genre();
        }
    }

    private void defineAuthorIfNull() {
        if (genre == null) {
            genre = new Genre();
        }
    }

    private void definePublisherIfNull() {
        if (genre == null) {
            genre = new Genre();
        }
    }

    private void defineBookLanguageIfNull() {
        if (genre == null) {
            genre = new Genre();
        }
    }
}