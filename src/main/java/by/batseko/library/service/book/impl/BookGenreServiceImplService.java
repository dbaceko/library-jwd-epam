package by.batseko.library.service.book.impl;

import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.service.book.BookComponentService;


public class BookGenreServiceImplService extends BaseBookService implements BookComponentService<Genre> {
    @Override
    public void add(Genre genre) throws LibraryServiceException {
        try {
            bookValidator.validateGenre(genre.getGenreTitle());
            genre.defineUUID();
            bookGenreDAO.add(genre);
            bookComponentsCache.getGenres().put(genre);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e);
        }
    }

    @Override
    public Genre findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookGenreDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e);
        }
    }
}
