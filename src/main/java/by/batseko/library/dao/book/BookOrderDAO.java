package by.batseko.library.dao.book;

import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookOrderDAO {
    void addBookOrder(BookOrder bookOrder) throws LibraryDAOException;

    void updateBookOrderStatus(BookOrder bookOrder) throws LibraryDAOException;

    List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryDAOException;
    List<BookOrder> findAllOrders() throws LibraryDAOException;
    List<BookOrder> findAllOpenedRequestsOrders() throws LibraryDAOException;
}
