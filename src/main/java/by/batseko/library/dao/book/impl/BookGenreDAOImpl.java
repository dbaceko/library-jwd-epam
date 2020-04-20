package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookGenreDAO;
import by.batseko.library.entity.book.Genre;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookGenreDAOImpl extends BaseDAO implements BookGenreDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookGenreDAOImpl.class);

    @Override
    public void addBookGenre(Genre genre) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_AUTHOR)) {
            preparedStatement.setString(1, genre.getUuid());
            preparedStatement.setString(2, genre.getGenre());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.genre.creation.alreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.genre.creation.commonError", e);
        }
    }

    @Override
    public Genre findBookGenreByUUID(String bookGenreUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_AUTHOR_BY_UUID)) {
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
    public List<Genre> findAllBookGenres() throws LibraryDAOException {
        List<Genre> genres;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOK_AUTHORS);
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

    private Genre constructGenreByResultSet(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setUuid(resultSet.getString(1));
        genre.setGenre(resultSet.getString(2));
        return genre;
    }
}
