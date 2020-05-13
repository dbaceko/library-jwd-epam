package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.book.BookComponentService;
import by.batseko.library.validatior.BookValidator;


public class BookGenreServiceImpl implements BookComponentService<Genre> {
    private final BookComponentDAO<Genre> bookGenreDAO;
    private final BookValidator bookValidator;
    private final CommonBookComponentsCache bookComponentsCache;

    public BookGenreServiceImpl() {
        bookGenreDAO = DAOFactory.getInstance().getBookGenreDAO();
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = CommonBookComponentsCache.getInstance();
    }

    @Override
    public void add(Genre genre) throws LibraryServiceException {
        if (genre == null) {
            throw new LibraryServiceException("service.commonError");
        }
        try {
            bookValidator.validateGenre(genre.getGenreTitle());
            genre.defineUUID();
            bookGenreDAO.add(genre);
            bookComponentsCache.getGenres().put(genre);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Genre findByUUID(String uuid) throws LibraryServiceException {
        if (uuid == null) {
            throw new LibraryServiceException("service.commonError");
        }
        try {
            return bookGenreDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }
}
