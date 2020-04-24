package by.batseko.library.service.book;

import by.batseko.library.entity.book.Book;
import by.batseko.library.exception.LibraryServiceException;

import java.util.List;

public interface BookService {
    void add(Book book) throws LibraryServiceException;
    Book findByUUID(String uuid) throws LibraryServiceException;
    List<Book> findAll() throws LibraryServiceException;
}
