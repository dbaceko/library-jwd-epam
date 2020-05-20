package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.BookDAO;
import by.batseko.library.dto.BookDTO;
import by.batseko.library.entity.book.Book;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.book.BookService;
import by.batseko.library.validatior.BookValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final CommonBookComponentsCache bookComponentsCache;

    public BookServiceImpl() {
        bookDAO = DAOFactory.getInstance().getBookDAO();
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = CommonBookComponentsCache.getInstance();
    }

    @Override
    public void add(Book book, int quantity) throws LibraryServiceException {
        if (book == null) {
            LOGGER.warn("book is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            bookValidator.validateNewBook(book);
            bookValidator.validateBookQuantity(quantity);
            book.setUuid(UUID.randomUUID().toString());
            bookDAO.addBook(book, quantity);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Book findByUUID(String uuid) throws LibraryServiceException {
        if (uuid == null) {
            LOGGER.warn("uuid is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            return bookDAO.findBookByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookDTO> findByFields(Book book, int currentPage, int recordsPerPage) throws LibraryServiceException {
        if (book == null) {
            LOGGER.warn("book is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            return bookDAO.findBooksDTOByFields(book, currentPage, recordsPerPage);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int findBookQuantityByFields(Book book) throws LibraryServiceException {
        if (book == null) {
            LOGGER.warn("book is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            return bookDAO.findBookQuantityByFields(book);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookDTO> findAllBookDTO(int currentPage, int recordsPerPage) throws LibraryServiceException {
        try {
            return bookDAO.findAllBooksDTO(currentPage, recordsPerPage);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public CommonBookComponentsCache getBookComponentsCache() {
        return bookComponentsCache;
    }
}
