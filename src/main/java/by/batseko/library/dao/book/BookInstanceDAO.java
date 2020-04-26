package by.batseko.library.dao.book;

import by.batseko.library.exception.LibraryDAOException;

import java.sql.Connection;

public interface BookInstanceDAO{
    void addBookInstance(String bookUUID, int quantity, Connection connection) throws LibraryDAOException;
}
