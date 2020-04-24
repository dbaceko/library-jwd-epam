package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.*;
import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.validatior.BookValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseBookService {
    private static final Logger LOGGER = LogManager.getLogger(BaseBookService.class);

    protected final BookValidator bookValidator;
    protected final CommonBookComponentsCache bookComponentsCache;
    protected final BookDAO bookDAO;
    protected final BookComponentDAO<Author> bookAuthorDAO;
    protected final BookComponentDAO<Genre> bookGenreDAO;
    protected final BookComponentDAO<Publisher> bookPublisherDAO;
    protected final BookComponentDAO<BookLanguage> bookLanguageDAO;

    protected BaseBookService() {
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = new CommonBookComponentsCache();
        bookDAO = DAOFactory.getInstance().getBookDAO();
        bookAuthorDAO = DAOFactory.getInstance().getBookAuthorDAO();
        bookGenreDAO = DAOFactory.getInstance().getBookGenreDAO();
        bookPublisherDAO = DAOFactory.getInstance().getBookPublisherDAO();
        bookLanguageDAO = DAOFactory.getInstance().getBookLanguageDAO();
        initBookComponentsCache();
    }

    private void initBookComponentsCache() {
        try {
            bookComponentsCache.getAuthors().putAllElements(bookAuthorDAO.findAll());
            bookComponentsCache.getGenres().putAllElements(bookGenreDAO.findAll());
            bookComponentsCache.getPublishers().putAllElements(bookPublisherDAO.findAll());
            bookComponentsCache.getBookLanguages().putAllElements(bookLanguageDAO.findAll());
        } catch (LibraryDAOException e) {
            LOGGER.error("CommonBookComponentsCache initialization error ", e);
        }
    }

    public CommonBookComponentsCache getBookComponentsCache() {
        return bookComponentsCache;
    }
}
