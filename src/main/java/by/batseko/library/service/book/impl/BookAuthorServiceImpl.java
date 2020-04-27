package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.book.BookComponentService;
import by.batseko.library.validatior.BookValidator;

public class BookAuthorServiceImpl implements BookComponentService<Author> {
    private final BookComponentDAO<Author> bookAuthorDAO;
    private final BookValidator bookValidator;
    private final CommonBookComponentsCache bookComponentsCache;

    public BookAuthorServiceImpl() {
        bookAuthorDAO = DAOFactory.getInstance().getBookAuthorDAO();
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = CommonBookComponentsCache.getInstance();
    }

    @Override
    public void add(Author author) throws LibraryServiceException {
        try {
            bookValidator.validateAuthor(author.getAuthorName());
            author.defineUUID();
            bookAuthorDAO.add(author);
            bookComponentsCache.getAuthors().put(author);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Author findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookAuthorDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }
}
