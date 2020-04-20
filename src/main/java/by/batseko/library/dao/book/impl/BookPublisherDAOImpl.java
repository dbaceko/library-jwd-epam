package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookPublisherDAO;
import by.batseko.library.entity.book.Publisher;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookPublisherDAOImpl extends BaseDAO implements BookPublisherDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookPublisherDAOImpl.class);

    @Override
    public void addBookPublisher(Publisher publisher) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_AUTHOR)) {
            preparedStatement.setString(1, publisher.getUuid());
            preparedStatement.setString(2, publisher.getPublisherTitle());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.publisher.creation.alreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.publisher.creation.commonError", e);
        }
    }

    @Override
    public Publisher findBookPublisherByUUID(String bookPublisherUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_AUTHOR_BY_UUID)) {
            preparedStatement.setString(1, bookPublisherUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructPublisherByResultSet(resultSet);
            } else {
                LOGGER.debug(String.format("Publisher not found by uuid %s", bookPublisherUUID));
                throw new LibraryDAOException("query.publisher.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<Publisher> findAllBookPublishers() throws LibraryDAOException {
        List<Publisher> publishers;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOK_AUTHORS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                publishers = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                LOGGER.info(listSize);
                publishers = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    Publisher publisher = constructPublisherByResultSet(resultSet);
                    LOGGER.info(publisher);
                    publishers.add(publisher);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
        return publishers;
    }

    private Publisher constructPublisherByResultSet(ResultSet resultSet) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setUuid(resultSet.getString(1));
        publisher.setPublisherTitle(resultSet.getString(2));
        return publisher;
    }
}
