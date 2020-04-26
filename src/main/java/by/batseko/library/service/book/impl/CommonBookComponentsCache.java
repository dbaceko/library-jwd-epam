package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.factory.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonBookComponentsCache {
    private static final Logger LOGGER = LogManager.getLogger(CommonBookComponentsCache.class);

    private final BookComponentStorage<Author> authors;
    private final BookComponentStorage<Publisher> publishers;
    private final BookComponentStorage<Genre> genres;
    private final BookComponentStorage<BookLanguage> bookLanguages;
    private final BookComponentDAO<Author> bookAuthorDAO;
    private final BookComponentDAO<Genre> bookGenreDAO;
    private final BookComponentDAO<Publisher> bookPublisherDAO;
    private final BookComponentDAO<BookLanguage> bookLanguageDAO;

    private CommonBookComponentsCache() {
        authors = new BookComponentStorage<>();
        publishers = new BookComponentStorage<>();
        genres = new BookComponentStorage<>();
        bookLanguages = new BookComponentStorage<>();

        bookAuthorDAO = DAOFactory.getInstance().getBookAuthorDAO();
        bookGenreDAO = DAOFactory.getInstance().getBookGenreDAO();
        bookPublisherDAO = DAOFactory.getInstance().getBookPublisherDAO();
        bookLanguageDAO = DAOFactory.getInstance().getBookLanguageDAO();
    }

    private static class CommonBookComponentsCacheHolder {
        static final CommonBookComponentsCache INSTANCE = new CommonBookComponentsCache();
    }

    public static CommonBookComponentsCache getInstance() {
        return CommonBookComponentsCacheHolder.INSTANCE;
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

    public void initBookComponentsCache() {
        try {
            authors.putAllElements(bookAuthorDAO.findAll());
            genres.putAllElements(bookGenreDAO.findAll());
            publishers.putAllElements(bookPublisherDAO.findAll());
            bookLanguages.putAllElements(bookLanguageDAO.findAll());
        } catch (LibraryDAOException e) {
            LOGGER.error("CommonBookComponentsCache initialization error ", e);
        }
    }
}
