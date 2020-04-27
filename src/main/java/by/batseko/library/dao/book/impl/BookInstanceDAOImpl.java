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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BookInstanceDAOImpl  extends BaseDAO implements BookInstanceDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookInstanceDAOImpl.class);

    static void addBookInstance(String bookUUID, int quantity, Connection connection) throws LibraryDAOException {
        for (int i = 0; i < quantity; i++) {
            LOGGER.info(String.format("Try to add %d book instance", i));
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_INSTANCE)) {
                preparedStatement.setString(1, UUID.randomUUID().toString());
                preparedStatement.setString(2, bookUUID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new LibraryDAOException("query.bookInstance.creation.commonError", e);
            }
        }
    }

    static void updateBookInstanceAvailableStatus(String bookInstanceUUID, boolean isAvailable, Connection connection) throws LibraryDAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_BOOK_INSTANCE_AVAILABLE_STATUS)) {
            preparedStatement.setBoolean(1, isAvailable);
            preparedStatement.setString(2, bookInstanceUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new LibraryDAOException("query.bookInstance.update.status", e);
        }

    }

    @Override
    public int findAvailableBooksQuantityByUUID(String bookUUID) throws LibraryDAOException {
        return findBooksQuantityByUUID(bookUUID, SQLQueriesStorage.FIND_AVAILABLE_BOOKS_QUANTITY_BY_UUID);
    }

    @Override
    public int findBooksQuantityByUUID(String bookUUID) throws LibraryDAOException {
        return findBooksQuantityByUUID(bookUUID, SQLQueriesStorage.FIND_BOOKS_QUANTITY_BY_UUID);
    }

    private int findBooksQuantityByUUID(String bookUUID, String sqlQuery) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setString(1, bookUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                LOGGER.debug(String.format("Book not found by uuid %s", bookUUID));
                throw new LibraryDAOException("query.book.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }
}



