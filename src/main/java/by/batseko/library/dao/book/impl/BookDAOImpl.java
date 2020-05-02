package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookDAO;
import by.batseko.library.dto.BookDTO;
import by.batseko.library.entity.book.Book;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDAOImpl extends BaseDAO implements BookDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookDAOImpl.class);

    private static final String TOTAL_BOOK_INSTANCE_QUANTITY_COLUMN_NAME = "total_book_instance_quantity";
    private static final String AVAILABLE_BOOK_INSTANCE_QUANTITY_COLUMN_NAME = "available_book_instance_quantity";

    @Override
    public void addBook(Book book, int quantity) throws LibraryDAOException {
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
            BookInstanceDAOImpl.addBookInstance(book.getUuid(), quantity, connection);
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.info(String.format("Book %s is already exist", book));
            BookInstanceDAOImpl.addBookInstance(getBookUUIDFromBookFields(book, connection), quantity, connection);
            connectionCommitChanges(connection);
        } catch (SQLException e) {
            connectionsRollback(connection);
            throw new LibraryDAOException("query.book.creation.commonError", e);
        } finally {
            connectionSetAutoCommit(connection, true);
            closeConnection(connection);
        }
    }

    @Override
    public Book findBookByUUID(String bookUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_BY_UUID)){
            preparedStatement.setString(1, bookUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructBookByResultSet(resultSet);
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

    @Override
    public List<BookDTO> findBooksDTOByFields(Book book) throws LibraryDAOException {
        return findBooksDTOByQuery(constructFindQueryFromBook(book));
    }

    @Override
    public List<BookDTO> findAllBooksDTO() throws LibraryDAOException {
        return findBooksDTOByQuery(SQLQueriesStorage.FIND_ALL_BOOKS);
    }

    private List<BookDTO> findBooksDTOByQuery(String query) throws LibraryDAOException {
        List<BookDTO> bookDTOList;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.last();
            int listSize = resultSet.getRow();
            resultSet.beforeFirst();
            bookDTOList = new ArrayList<>(listSize);
            BookDTO currentBookDTO;
            while (resultSet.next()) {
                currentBookDTO = new BookDTO();
                currentBookDTO.setBook(constructBookByResultSet(resultSet));
                currentBookDTO.setTotalAvailableBooksQuantity(resultSet.getInt(AVAILABLE_BOOK_INSTANCE_QUANTITY_COLUMN_NAME));
                currentBookDTO.setTotalBooksQuantity(resultSet.getInt(TOTAL_BOOK_INSTANCE_QUANTITY_COLUMN_NAME));
                LOGGER.info(currentBookDTO);
                bookDTOList.add(currentBookDTO);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
        if (bookDTOList.size() == 1 && bookDTOList.get(0).getBook().getUuid() == null) {
                bookDTOList = Collections.emptyList();
        }
        return bookDTOList;
    }

    private String getBookUUIDFromBookFields(Book book, Connection connection) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_UUID_BY_FIELDS)){
            preparedStatement.setString(1, book.getGenre().getUuid());
            preparedStatement.setString(2, book.getBookLanguage().getUuid());
            preparedStatement.setString(3, book.getPublisher().getUuid());
            preparedStatement.setString(4, book.getAuthor().getUuid());
            preparedStatement.setString(5, book.getTitle());
            preparedStatement.setInt(6, book.getPublishYear());
            preparedStatement.setInt(7, book.getPagesQuantity());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                LOGGER.debug(String.format("Book %s not found by ", book));
                throw new LibraryDAOException("query.book.read.notFound");
            }
        } catch (SQLException | LibraryDAOException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }
}
