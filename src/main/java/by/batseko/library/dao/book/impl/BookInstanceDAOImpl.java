package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookInstanceDAO;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookInstanceDAOImpl  extends BaseDAO implements BookInstanceDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookInstanceDAOImpl.class);

    @Override
    public void addBookInstance(String bookUUID) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_INSTANCE)) {
            preparedStatement.setString(1, bookUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.genre.creation.commonError", e);
        }
    }
}
