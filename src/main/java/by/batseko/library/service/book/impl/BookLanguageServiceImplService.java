package by.batseko.library.service.book.impl;

import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.service.book.BookComponentService;


public class BookLanguageServiceImplService extends BaseBookService implements BookComponentService<BookLanguage> {
    @Override
    public void add(BookLanguage bookLanguage) throws LibraryServiceException {
        try {
            bookValidator.validateLanguage(bookLanguage.getLanguageTitle());
            bookLanguage.defineUUID();
            bookLanguageDAO.add(bookLanguage);
            bookComponentsCache.getBookLanguages().put(bookLanguage);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e);
        }
    }

    @Override
    public BookLanguage findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookLanguageDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e);
        }
    }
}
