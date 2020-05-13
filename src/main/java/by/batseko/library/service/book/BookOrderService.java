package by.batseko.library.service.book;

import by.batseko.library.entity.book.Book;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.book.impl.BookOrdersCache;

import java.util.List;

/**
 * Interface describes the behavior of {@link BookOrder} entity
 */
public interface BookOrderService {

    /**
     * Saves {@link BookOrder} into cache if user online and into database
     *
     * @param bookOrder {@link BookOrder} instance
     * @throws LibraryServiceException if <tt>bookOrder</tt> is null
     *                          or if an error occurs while writing new {@link BookOrder}
     *                          into data source
     */
    void addBookOrder(BookOrder bookOrder) throws LibraryServiceException;

    /**
     * Update {@link BookOrder}'s status into cache if user online and into database
     *
     * @param bookOrder {@link BookOrder} instance
     * @throws LibraryServiceException if <tt>bookOrder</tt> is null
     *                          or if an error occurs while writing {@link BookOrder}'s
     *                          status into data source
     */
    void updateBookOrderStatus(BookOrder bookOrder) throws LibraryServiceException;

    /**
     * Find BookOrder list of {@link List<BookOrder>} which user id is <tt>userId</tt>
     *
     * @param userId is the {@link BookOrder}'s {@link User}'s id
     * @return BookOrder list {@link List<BookOrder>} which user id is <tt>userId</tt>
     * @throws LibraryServiceException if {@link List<BookOrder>} is empty
     *                          or occurs after searching {@link BookOrder} into the data source
     */
    List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryServiceException;

    /**
     * Find BookOrder list of {@link List<BookOrder>} which user id is <tt>userId</tt>
     *
     * @param userId is the {@link BookOrder}'s {@link User}'s id
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return BookOrder list {@link List<BookOrder>} which user id is <tt>userId</tt>
     * @throws LibraryServiceException if {@link List<BookOrder>} is empty
     *                          or occurs after searching {@link BookOrder} into the data source
     */
    List<BookOrder> findOrdersByUserId(int userId, int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Find quantity of {@link List<BookOrder>} which follow the book fields pattern
     *
     * @param userId is the {@link BookOrder}'s {@link User}'s id
     * @return quantity of {@link List<BookOrder>}  which user id is <tt>userId</tt>
     * @throws LibraryServiceException occurs after searching {@link Book} into the data source
     */
    int findOrdersQuantityByUserId(int userId) throws LibraryServiceException;

    /**
     * Find BookOrder list of {@link List<BookOrder>} which status is pending or return
     *
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return BookOrder list {@link List<BookOrder>} which status is pending or return
     * @throws LibraryServiceException if {@link List<BookOrder>} is empty
     *                          or occurs after searching {@link BookOrder} into the data source
     */
    List<BookOrder> findAllOpenOrders(int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Find quantity of all {@link List<BookOrder>} which status is pending or return
     *
     * @return quantity of all {@link List<BookOrder>} which status is pending or return
     * @throws LibraryServiceException occurs after searching {@link Book} into the data source
     */
    int findOpenOrdersQuantity() throws LibraryServiceException;

    /**
     * Returns {@link BookOrdersCache} which contains {@link BookOrder}'s of online {@link User}'s
     *
     * @return {@link BookOrdersCache} which contains {@link BookOrder}'s of online {@link User}'s
     */
    BookOrdersCache getBookOrdersCache();
}
