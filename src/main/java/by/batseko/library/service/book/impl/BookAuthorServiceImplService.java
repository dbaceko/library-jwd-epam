package by.batseko.library.service.book.impl;

import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.service.book.BookComponentService;

public class BookAuthorServiceImplService extends BaseBookService implements BookComponentService<Author> {
    @Override
    public void add(Author author) throws LibraryServiceException {
        try {
            bookValidator.validateAuthor(author.getAuthorName());
            author.defineUUID();
            bookAuthorDAO.add(author);
            bookComponentsCache.getAuthors().put(author);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e);
        }
    }

    @Override
    public Author findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookAuthorDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e);
        }
    }


}
