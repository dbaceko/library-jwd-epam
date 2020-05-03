package by.batseko.library.dao.book;

import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookOrderDAO {
    void addBookOrder(BookOrder bookOrder) throws LibraryDAOException;

    void updateBookOrderStatus(BookOrder bookOrder) throws LibraryDAOException;
    void cancelBookOrder(BookOrder bookOrder) throws LibraryDAOException;

    BookOrder findOrderByUUID(String uuid) throws LibraryDAOException;
    int findOrdersQuantityByUserId(int userId) throws LibraryDAOException;
    List<BookOrder> findOrdersByUserId(int userId, int currentPage, int recordsPerPage) throws LibraryDAOException;
    List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryDAOException;
    int findOpenOrdersQuantity() throws LibraryDAOException;
    List<BookOrder> findAllOpenedOrders(int currentPage, int recordsPerPage) throws LibraryDAOException;
}
