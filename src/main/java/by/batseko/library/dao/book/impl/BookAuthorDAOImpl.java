package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookAuthorDAOImpl extends BaseDAO implements BookComponentDAO<Author> {
    private static final Logger LOGGER = LogManager.getLogger(BookAuthorDAOImpl.class);

    @Override
    public void add(Author author) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_AUTHOR)) {
            preparedStatement.setString(1, author.getUuid());
            preparedStatement.setString(2, author.getAuthorName());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.author.creation.alreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.author.creation.commonError", e);
        }
    }

    @Override
    public Author findByUUID(String bookAuthorUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_AUTHOR_BY_UUID)) {
            preparedStatement.setString(1, bookAuthorUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructAuthorByResultSet(resultSet);
            } else {
                LOGGER.debug(String.format("Author not found by uuid %s", bookAuthorUUID));
                throw new LibraryDAOException("query.author.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<Author> findAll() throws LibraryDAOException {
        List<Author> authors;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOK_AUTHORS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                authors = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                LOGGER.info(listSize);
                authors = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    Author author = constructAuthorByResultSet(resultSet);
                    LOGGER.info(author);
                    authors.add(author);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
        return authors;
    }
}