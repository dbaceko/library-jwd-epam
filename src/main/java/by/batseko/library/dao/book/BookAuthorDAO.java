package by.batseko.library.dao.book;

import by.batseko.library.entity.book.Author;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookAuthorDAO {
    void addBookAuthor(Author author) throws LibraryDAOException;
    Author findBookAuthorByUUID(String bookAuthorUUID) throws LibraryDAOException;
    List<Author> findAllBookAuthors() throws LibraryDAOException;
}
