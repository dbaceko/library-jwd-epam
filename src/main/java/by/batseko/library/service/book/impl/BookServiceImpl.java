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
        try {
            bookValidator.validateNewBook(book);
            bookValidator.validateBookQuantity(quantity);
            LOGGER.info(String.format("Try to add book: %s", book));
            book.setGenre(bookComponentsCache.getGenres().get(book.getGenre().getUuid()));
            book.setAuthor(bookComponentsCache.getAuthors().get(book.getAuthor().getUuid()));
            book.setBookLanguage(bookComponentsCache.getBookLanguages().get(book.getBookLanguage().getUuid()));
            book.setPublisher(bookComponentsCache.getPublishers().get(book.getPublisher().getUuid()));
            book.defineUUID();
            bookDAO.addBook(book, quantity);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Book findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookDAO.findBookByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookDTO> findAllBookDTO() throws LibraryServiceException {
        try {
            return bookDAO.findAllBooksDTO();
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public CommonBookComponentsCache getBookComponentsCache() {
        return bookComponentsCache;
    }
}
