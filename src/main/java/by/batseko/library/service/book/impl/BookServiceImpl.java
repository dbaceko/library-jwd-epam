package by.batseko.library.service.book.impl;

import by.batseko.library.entity.book.Book;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.service.book.BookService;

import java.util.List;

public class BookServiceImpl extends BaseBookService implements BookService {
    @Override
    public void add(Book book) throws LibraryServiceException {
        try {
            bookValidator.validateNewBook(book);
            book.setGenre(bookComponentsCache.getGenres().get(book.getGenre().getUuid()));
            book.setAuthor(bookComponentsCache.getAuthors().get(book.getAuthor().getUuid()));
            book.setBookLanguage(bookComponentsCache.getBookLanguages().get(book.getBookLanguage().getUuid()));
            book.setPublisher(bookComponentsCache.getPublishers().get(book.getPublisher().getUuid()));
            book.defineUUID();
            bookDAO.addBook(book);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e);
        }
    }

    @Override
    public Book findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookDAO.findBookByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e);
        }
    }

    @Override
    public List<Book> findAll() throws LibraryServiceException {
        try {
            return bookDAO.findAllBooks();
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e);
        }
    }
}
