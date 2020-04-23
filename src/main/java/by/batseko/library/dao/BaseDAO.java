package by.batseko.library.dao;

import by.batseko.library.builder.book.BookBuilder;
import by.batseko.library.entity.book.*;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
    private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);

    private static final String BOOK_AUTHOR_UUID_COLUMN_NAME = "book_author.uuid";
    private static final String BOOK_AUTHOR_FIRSTNAME_COLUMN_NAME = "book_author.firstname";
    private static final String BOOK_AUTHOR_LASTNAME_COLUMN_NAME = "book_author.lastname";

    private static final String BOOK_GENRE_UUID_COLUMN_NAME = "book_genre.uuid";
    private static final String BOOK_GENRE_GENRE_COLUMN_NAME = "book_genre.genre";

    private static final String BOOK_LANGUAGE_UUID_COLUMN_NAME = "book_language.uuid";
    private static final String BOOK_LANGUAGE_COLUMN_NAME = "book_language.language";

    private static final String PUBLISHER_UUID_COLUMN_NAME = "book_publisher.uuid";
    private static final String PUBLISHER_COLUMN_NAME = "book_publisher.title";

    private static final String BOOK_UUID_COLUMN_NAME = "book.uuid";
    private static final String BOOK_TITLE_COLUMN_NAME = "book.title";
    private static final String BOOK_PUBLISH_YEAR_COLUMN_NAME = "book.publish_year";
    private static final String BOOK_PAGES_QUANTITY_COLUMN_NAME = "book.pages_quantity";
    private static final String BOOK_PAGES_DESCRIPTION_COLUMN_NAME = "book.description";
    private static final String BOOK_AVAILABLE_QUANTITY_COLUMN_NAME = "book.available_book_quantity";

    protected final ConnectionPool pool;

    protected BaseDAO(){
        pool = ConnectionPool.getInstance();
    }

    protected void closeResultSet(ResultSet resultSet) throws LibraryDAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Can`t close resultSet", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionCommitChanges(Connection connection) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error("Can`t commit connection's changes", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionSetAutoCommit(Connection connection, boolean value) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.setAutoCommit(value);
            } catch (SQLException e) {
                LOGGER.error("Can`t set autocommmit to connection", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionsRollback(Connection connection) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Can`t rollback connection result", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected Author constructAuthorByResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setUuid(resultSet.getString(BOOK_AUTHOR_UUID_COLUMN_NAME));
        author.setFristname(resultSet.getString(BOOK_AUTHOR_FIRSTNAME_COLUMN_NAME));
        author.setLastname(resultSet.getString(BOOK_AUTHOR_LASTNAME_COLUMN_NAME));
        return author;
    }

    protected Genre constructGenreByResultSet(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setUuid(resultSet.getString(BOOK_GENRE_UUID_COLUMN_NAME));
        genre.setGenre(resultSet.getString(BOOK_GENRE_GENRE_COLUMN_NAME));
        return genre;
    }

    protected BookLanguage constructBookLanguageByResultSet(ResultSet resultSet) throws SQLException {
        BookLanguage bookLanguage = new BookLanguage();
        bookLanguage.setUuid(resultSet.getString(BOOK_LANGUAGE_UUID_COLUMN_NAME));
        bookLanguage.setLanguage(resultSet.getString(BOOK_LANGUAGE_COLUMN_NAME));
        return bookLanguage;
    }

    protected Publisher constructPublisherByResultSet(ResultSet resultSet) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setUuid(resultSet.getString(PUBLISHER_UUID_COLUMN_NAME));
        publisher.setPublisherTitle(resultSet.getString(PUBLISHER_COLUMN_NAME));
        return publisher;
    }

    protected Book constructBookByResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder().setUuid(resultSet.getString(BOOK_UUID_COLUMN_NAME))
                .setGenre(constructGenreByResultSet(resultSet))
                .setBookLanguage(constructBookLanguageByResultSet(resultSet))
                .setPublisher(constructPublisherByResultSet(resultSet))
                .setAuthor(constructAuthorByResultSet(resultSet))
                .setTitle(resultSet.getString(BOOK_TITLE_COLUMN_NAME))
                .setPublishYear(resultSet.getInt(BOOK_PUBLISH_YEAR_COLUMN_NAME))
                .setPagesQuantity(resultSet.getInt(BOOK_PAGES_QUANTITY_COLUMN_NAME))
                .setDescription(resultSet.getString(BOOK_PAGES_DESCRIPTION_COLUMN_NAME))
                .setAvailableBookQuantity(resultSet.getInt(BOOK_AVAILABLE_QUANTITY_COLUMN_NAME))
                .build();
    }
}
