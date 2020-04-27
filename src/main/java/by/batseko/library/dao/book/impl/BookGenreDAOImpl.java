package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookComponentDAO;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookGenreDAOImpl extends BaseDAO implements BookComponentDAO<Genre> {
    private static final Logger LOGGER = LogManager.getLogger(BookGenreDAOImpl.class);

    @Override
    public void add(Genre genre) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_GENRE)) {
            preparedStatement.setString(1, genre.getUuid());
            preparedStatement.setString(2, genre.getGenreTitle());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.genre.creation.alreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.genre.creation.commonError", e);
        }
    }

    @Override
    public Genre findByUUID(String bookGenreUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_GENRE_BY_UUID)) {
            preparedStatement.setString(1, bookGenreUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructGenreByResultSet(resultSet);
            } else {
                LOGGER.debug(String.format("Genre not found by uuid %s", bookGenreUUID));
                throw new LibraryDAOException("query.genre.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<Genre> findAll() throws LibraryDAOException {
        List<Genre> genres;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOK_GENRES);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                genres = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                genres = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    Genre genre = constructGenreByResultSet(resultSet);
                    LOGGER.info(genre);
                    genres.add(genre);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
        return genres;
    }
}
