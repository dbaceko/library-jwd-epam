package by.batseko.library.dao.book;

import by.batseko.library.entity.book.Book;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookDAO {
    void addBook(Book book, int quantity) throws LibraryDAOException;
    Book findBookByUUID(String bookUUID) throws LibraryDAOException;
    List<Book> findAllBooks() throws LibraryDAOException;
}
