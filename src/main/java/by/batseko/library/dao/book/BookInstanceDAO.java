package by.batseko.library.dao.book;

import by.batseko.library.exception.LibraryDAOException;

public interface BookInstanceDAO{
    int findAvailableBooksQuantityByUUID(String bookUUID) throws LibraryDAOException;
    int findBooksQuantityByUUID(String bookUUID) throws LibraryDAOException;
}
