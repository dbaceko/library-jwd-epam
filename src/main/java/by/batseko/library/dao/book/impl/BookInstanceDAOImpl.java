package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookInstanceDAO;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class BookInstanceDAOImpl  extends BaseDAO implements BookInstanceDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookInstanceDAOImpl.class);

    @Override
    public void addBookInstance(String bookUUID, int quantity, Connection connection) throws LibraryDAOException {
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
}
