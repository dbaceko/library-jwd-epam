package by.batseko.library.service.book.impl;

import by.batseko.library.dao.book.BookInstanceDAO;
import by.batseko.library.dao.book.BookOrderDAO;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.entity.order.OrderStatus;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.service.book.BookOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class BookOrderServiceImpl implements BookOrderService {
    private static final Logger LOGGER = LogManager.getLogger(BookOrderServiceImpl.class);

    private final BookOrderDAO bookOrderDAO;
    private final BookInstanceDAO bookInstanceDAO;
    private final BookOrdersCache bookOrdersCache;

    public BookOrderServiceImpl(){
        bookOrderDAO = DAOFactory.getInstance().getBookOrderDAO();
        bookInstanceDAO = DAOFactory.getInstance().getBookInstanceDAO();
        bookOrdersCache = BookOrdersCache.getInstance();
    }

    @Override
    public void addBookOrder(BookOrder bookOrder) throws LibraryServiceException {
        List<String> availableBookInstanceUUIDs;
        try {
            String bookUUID = bookOrder.getBookInstance().getBook().getUuid();
            availableBookInstanceUUIDs = bookInstanceDAO.findAllAvailableBookInstanceUUIDsByBookUUID(bookUUID);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
        LibraryServiceException availableInstancesEndedException = new LibraryServiceException("query.bookInstance.find.empty");
        for (int i = 0; i < availableBookInstanceUUIDs.size(); i++) {
            try {
                bookOrder.setUuid(UUID.randomUUID().toString());
                bookOrder.getBookInstance().setUuid(availableBookInstanceUUIDs.get(i));
                bookOrderDAO.addBookOrder(bookOrder);
                BookOrdersCache.UserBookOrdersMap currentUserCachedOrders = bookOrdersCache.get(bookOrder.getUser().getLogin());
                if (currentUserCachedOrders != null) {
                    currentUserCachedOrders.put(bookOrder.getUuid(), findOrderByUUID(bookOrder.getUuid()));
                }
                return;
            } catch (LibraryDAOException e) {
                if (i == availableBookInstanceUUIDs.size() - 1) {
                    LOGGER.warn(String.format("All books %s is already booked", bookOrder.getBookInstance().getBook()));
                    availableInstancesEndedException = new LibraryServiceException(e.getMessage(), e);
                } else {
                    LOGGER.info(String.format("Book instance %s is already booked", bookOrder.getBookInstance()));
                }
            }
        }
        throw availableInstancesEndedException;
    }

    @Override
    public void cancelBookOrder(BookOrder bookOrder) throws LibraryServiceException {
        bookOrder.setOrderStatus(OrderStatus.CLOSE);
        try {
            bookOrderDAO.cancelBookOrder(bookOrder);
            updateBookOrderCacheOrderStatus(bookOrder);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateBookOrderStatus(BookOrder bookOrder) throws LibraryServiceException {
        try {
            bookOrderDAO.updateBookOrderStatus(bookOrder);
            updateBookOrderCacheOrderStatus(bookOrder);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public BookOrder findOrderByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookOrderDAO.findOrderByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryServiceException {
        try {
            return bookOrderDAO.findAllOrdersByUserId(userId);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookOrder> findAllOrders() throws LibraryServiceException {
        try {
            return bookOrderDAO.findAllOrders();
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookOrder> findAllOpenedRequestsOrders() throws LibraryServiceException {
        try {
            return bookOrderDAO.findAllOpenedRequestsOrders();
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    public BookOrdersCache getBookOrdersCache() {
        return bookOrdersCache;
    }

    private void updateBookOrderCacheOrderStatus(BookOrder bookOrder) throws LibraryServiceException {
        BookOrdersCache.UserBookOrdersMap currentUserCachedOrders = bookOrdersCache.get(bookOrder.getUser().getLogin());
        if (currentUserCachedOrders != null) {
            currentUserCachedOrders.get(bookOrder.getUuid()).setOrderStatus(bookOrder.getOrderStatus());
        }
    }
}
