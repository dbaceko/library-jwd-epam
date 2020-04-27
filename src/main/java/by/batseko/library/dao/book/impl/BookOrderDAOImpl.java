package by.batseko.library.dao.book.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.book.BookOrderDAO;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookOrderDAOImpl extends BaseDAO implements BookOrderDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookOrderDAOImpl.class);

    @Override
    public void addBookOrder(BookOrder bookOrder) throws LibraryDAOException {
        Connection connection;
        try {
            connection = pool.getConnection();
        } catch (ConnectionPoolException e) {
            throw new LibraryDAOException("query.bookOrder.creation.commonError", e);
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_ORDER)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, bookOrder.getUuid());
            preparedStatement.setInt(2, bookOrder.getUser().getId());
            preparedStatement.setString(3, bookOrder.getBookInstance().getUuid());
            preparedStatement.setInt(4, bookOrder.getOrderType().getId());
            preparedStatement.executeUpdate();
            BookInstanceDAOImpl.updateBookInstanceAvailableStatus(bookOrder.getBookInstance().getUuid(), false, connection);
        } catch (SQLException e) {
            connectionsRollback(connection);
            throw new LibraryDAOException("query.bookOrder.creation.commonError", e);
        } finally {
            connectionSetAutoCommit(connection, true);
        }
    }

    @Override
    public void updateBookOrderStatus(BookOrder bookOrder) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_BOOK_ORDER_STATUS)) {
            preparedStatement.setInt(1, bookOrder.getOrderStatus().getId());
            preparedStatement.setString(2, bookOrder.getUuid());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.bookOrder.update.status", e);
        }
    }

    @Override
    public List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_ORDERS_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            return getAllOrdersFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<BookOrder> findAllOrders() throws LibraryDAOException {
        return findAllOrdersByQuery(SQLQueriesStorage.FIND_BOOK_ORDERS);
    }

    @Override
    public List<BookOrder> findAllOpenedRequestsOrders() throws LibraryDAOException {
        return findAllOrdersByQuery(SQLQueriesStorage.FIND_BOOK_ORDERS_WITH_OPEN_REQUEST);
    }

    private List<BookOrder> getAllOrdersFromResultSet(ResultSet resultSet) throws SQLException {
        List<BookOrder> bookOrders;
        if (!resultSet.isBeforeFirst()) {
            bookOrders = Collections.emptyList();
        } else {
            resultSet.last();
            int listSize = resultSet.getRow();
            resultSet.beforeFirst();
            bookOrders = new ArrayList<>(listSize);
            while (resultSet.next()) {
                BookOrder bookOrder = constructOrderByResultSet(resultSet);
                LOGGER.info(bookOrder);
                bookOrders.add(bookOrder);
            }
        }
        return bookOrders;
    }

    private List<BookOrder> findAllOrdersByQuery(String sqlQuery) throws LibraryDAOException{
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            return getAllOrdersFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
    }
}
