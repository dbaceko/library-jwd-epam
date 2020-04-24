package by.batseko.library.service.book.impl;

import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.entity.book.bookcomponent.Publisher;

class CommonBookComponentsCache {
    private final BookComponentStorage<Author> authors;
    private final BookComponentStorage<Publisher> publishers;
    private final BookComponentStorage<Genre> genres;
    private final BookComponentStorage<BookLanguage> bookLanguages;

    CommonBookComponentsCache() {
        authors = new BookComponentStorage<>();
        publishers = new BookComponentStorage<>();
        genres = new BookComponentStorage<>();
        bookLanguages = new BookComponentStorage<>();
    }

    public BookComponentStorage<Author> getAuthors() {
        return authors;
    }

    public BookComponentStorage<Publisher> getPublishers() {
        return publishers;
    }

    public BookComponentStorage<Genre> getGenres() {
        return genres;
    }

    public BookComponentStorage<BookLanguage> getBookLanguages() {
        return bookLanguages;
    }
}
