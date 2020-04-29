package by.batseko.library.service.book;

import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.book.impl.BookOrdersCache;

import java.util.List;

public interface BookOrderService {
    void addBookOrder(BookOrder bookOrder) throws LibraryServiceException;
    void updateBookOrderStatus(BookOrder bookOrder) throws LibraryServiceException;
    List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryServiceException;
    List<BookOrder> findAllOrders() throws LibraryServiceException;
    List<BookOrder> findAllOpenedRequestsOrders() throws LibraryServiceException;
    BookOrdersCache getBookOrdersCache();
}
