package by.batseko.library.dao.book;

import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookComponentDAO <T> {
    void add(T element) throws LibraryDAOException;
    T findByUUID(String bookAuthorUUID) throws LibraryDAOException;
    List<T> findAll() throws LibraryDAOException;
}
