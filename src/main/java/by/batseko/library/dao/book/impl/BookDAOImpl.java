package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookDAO;
import by.batseko.library.dao.book.BookInstanceDAO;
import by.batseko.library.entity.book.Book;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.factory.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDAOImpl extends BaseDAO implements BookDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookDAOImpl.class);

    private final BookInstanceDAO bookInstanceDAO;

    public BookDAOImpl() {
        bookInstanceDAO = DAOFactory.getInstance().getBookInstanceDAO();
    }

    @Override
    public void addBook(Book book) throws LibraryDAOException {
        Connection connection;
        try {
            connection = pool.getConnection();
        } catch (ConnectionPoolException e) {
            throw new LibraryDAOException("query.book.creation.commonError", e);
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, book.getUuid());
            preparedStatement.setString(2, book.getGenre().getUuid());
            preparedStatement.setString(3, book.getBookLanguage().getUuid());
            preparedStatement.setString(4, book.getPublisher().getUuid());
            preparedStatement.setString(5, book.getAuthor().getUuid());
            preparedStatement.setString(6, book.getTitle());
            preparedStatement.setInt(7, book.getPublishYear());
            preparedStatement.setInt(8, book.getPagesQuantity());
            preparedStatement.setString(9, book.getDescription());
            preparedStatement.executeUpdate();
            bookInstanceDAO.addBookInstance(book.getUuid());
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.info(String.format("Book %s is already exist", book));
            bookInstanceDAO.addBookInstance(book.getUuid());
            connectionCommitChanges(connection);
        } catch (SQLException e) {
            connectionsRollback(connection);
            throw new LibraryDAOException("query.book.creation.commonError", e);
        } finally {
            connectionSetAutoCommit(connection, true);
        }
    }

    @Override
    public Book findBookByUUID(String bookUUID) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_BY_UUID);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            return constructBookByResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public List<Book> findAllBooks() throws LibraryDAOException {
        List<Book> books;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOKS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                books = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                books = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    Book book = constructBookByResultSet(resultSet);
                    LOGGER.info(book);
                    books.add(book);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
        return books;
    }
}
