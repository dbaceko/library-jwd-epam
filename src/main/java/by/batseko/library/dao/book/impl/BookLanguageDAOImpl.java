package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookLanguageDAOImpl extends BaseDAO implements BookComponentDAO<BookLanguage> {
    private static final Logger LOGGER = LogManager.getLogger(BookLanguageDAOImpl.class);

    @Override
    public void add(BookLanguage bookLanguage) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_LANGUAGE)) {
            preparedStatement.setString(1, bookLanguage.getUuid());
            preparedStatement.setString(2, bookLanguage.getLanguageTitle());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.bookLanguage.creation.alreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.bookLanguage.creation.commonError", e);
        }
    }

    @Override
    public BookLanguage findByUUID(String bookLanguageUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_LANGUAGE_BY_UUID)) {
            preparedStatement.setString(1, bookLanguageUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructBookLanguageByResultSet(resultSet);
            } else {
                LOGGER.debug(String.format("Book language not found by uuid %s", bookLanguageUUID));
                throw new LibraryDAOException("query.bookLanguage.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<BookLanguage> findAll() throws LibraryDAOException {
        List<BookLanguage> bookLanguages;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOK_LANGUAGES);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                bookLanguages = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                bookLanguages = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    BookLanguage bookLanguage = constructBookLanguageByResultSet(resultSet);
                    LOGGER.info(bookLanguage);
                    bookLanguages.add(bookLanguage);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
        return bookLanguages;
    }

}
