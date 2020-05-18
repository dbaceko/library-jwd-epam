package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;

import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.book.BookComponentService;
import by.batseko.library.validatior.BookValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookPublisherServiceImpl implements BookComponentService<Publisher> {
    private static final Logger LOGGER = LogManager.getLogger(BookLanguageServiceImpl.class);

    private final BookComponentDAO<Publisher> bookPublisherDAO;
    private final BookValidator bookValidator;
    private final CommonBookComponentsCache bookComponentsCache;

    public BookPublisherServiceImpl() {
        bookPublisherDAO = DAOFactory.getInstance().getBookPublisherDAO();
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = CommonBookComponentsCache.getInstance();
    }

    @Override
    public void add(Publisher publisher) throws LibraryServiceException {
        if (publisher == null) {
            LOGGER.warn("publisher is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            bookValidator.validatePublisher(publisher.getPublisherTitle());
            publisher.defineUUID();
            bookPublisherDAO.add(publisher);
            bookComponentsCache.getPublishers().put(publisher);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Publisher findByUUID(String uuid) throws LibraryServiceException {
        if (uuid == null) {
            LOGGER.warn("uuid is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            return bookPublisherDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }
}
