package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.book.BookComponentService;
import by.batseko.library.validatior.BookValidator;


public class BookLanguageServiceImpl implements BookComponentService<BookLanguage> {
    private final BookComponentDAO<BookLanguage> bookLanguageDAO;
    private final BookValidator bookValidator;
    private final CommonBookComponentsCache bookComponentsCache;

    public BookLanguageServiceImpl() {
        bookLanguageDAO = DAOFactory.getInstance().getBookLanguageDAO();
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = CommonBookComponentsCache.getInstance();
    }

    @Override
    public void add(BookLanguage bookLanguage) throws LibraryServiceException {
        try {
            bookValidator.validateLanguage(bookLanguage.getLanguageTitle());
            bookLanguage.defineUUID();
            bookLanguageDAO.add(bookLanguage);
            bookComponentsCache.getBookLanguages().put(bookLanguage);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public BookLanguage findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookLanguageDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }
}
