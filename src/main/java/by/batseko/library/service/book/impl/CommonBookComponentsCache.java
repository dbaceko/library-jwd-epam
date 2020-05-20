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

    private final SortedBookComponentStorage<Author> authors;
    private final SortedBookComponentStorage<Publisher> publishers;
    private final SortedBookComponentStorage<Genre> genres;
    private final SortedBookComponentStorage<BookLanguage> bookLanguages;
    private final BookComponentDAO<Author> bookAuthorDAO;
    private final BookComponentDAO<Genre> bookGenreDAO;
    private final BookComponentDAO<Publisher> bookPublisherDAO;
    private final BookComponentDAO<BookLanguage> bookLanguageDAO;

    private CommonBookComponentsCache() {
        authors = new SortedBookComponentStorage<>();
        publishers = new SortedBookComponentStorage<>();
        genres = new SortedBookComponentStorage<>();
        bookLanguages = new SortedBookComponentStorage<>();

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

    public SortedBookComponentStorage<Author> getAuthors() {
        return authors;
    }

    public SortedBookComponentStorage<Publisher> getPublishers() {
        return publishers;
    }

    public SortedBookComponentStorage<Genre> getGenres() {
        return genres;
    }

    public SortedBookComponentStorage<BookLanguage> getBookLanguages() {
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
