package by.batseko.library.dao.book;

import by.batseko.library.exception.LibraryDAOException;

public interface BookInstanceDAO{
    void addBookInstance(String bookUUID) throws LibraryDAOException;
}
