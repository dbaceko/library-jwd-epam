package by.batseko.library.dao.book;

import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookInstanceDAO {
    int findAvailableBooksQuantityByUUID(String bookUUID) throws LibraryDAOException;
    int findBooksQuantityByUUID(String bookUUID) throws LibraryDAOException;
    List<String> findAllAvailableBookInstanceUUIDsByBoolUUID(String bookUUID) throws LibraryDAOException;
}
