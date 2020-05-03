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
        LibraryServiceException bookIsUnavailableException = new LibraryServiceException("query.bookInstance.find.empty");
        for (int i = 0; i < availableBookInstanceUUIDs.size(); i++) {
            try {
                bookOrder.setUuid(UUID.randomUUID().toString());
                bookOrder.getBookInstance().setUuid(availableBookInstanceUUIDs.get(i));
                bookOrderDAO.addBookOrder(bookOrder);
                BookOrdersCache.UserBookOrdersMap currentUserCachedOrders = bookOrdersCache.get(bookOrder.getUser().getLogin());
                if (currentUserCachedOrders != null) {
                    currentUserCachedOrders.put(bookOrder.getUuid(), bookOrderDAO.findOrderByUUID(bookOrder.getUuid()));
                }
                return;
            } catch (LibraryDAOException e) {
                if (i == availableBookInstanceUUIDs.size() - 1) {
                    LOGGER.warn(String.format("All books %s is already booked", bookOrder.getBookInstance().getBook()));
                    bookIsUnavailableException = new LibraryServiceException(e.getMessage(), e);
                } else {
                    LOGGER.info(String.format("Book instance %s is already booked", bookOrder.getBookInstance()));
                }
            }
        }
        throw bookIsUnavailableException;
    }

    @Override
    public void updateBookOrderStatus(BookOrder bookOrder) throws LibraryServiceException {
        try {
            if (bookOrder.getOrderStatus() == OrderStatus.CLOSE) {
                bookOrderDAO.cancelBookOrder(bookOrder);
            } else {
                bookOrderDAO.updateBookOrderStatus(bookOrder);
            }
            updateBookOrderCacheOrderStatus(bookOrder);
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
    public List<BookOrder> findOrdersByUserId(int userId, int currentPage, int recordsPerPage) throws LibraryServiceException {
        try {
            return bookOrderDAO.findOrdersByUserId(userId, currentPage, recordsPerPage);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int findOrdersQuantityByUserId(int userId) throws LibraryServiceException {
        try {
            return bookOrderDAO.findOrdersQuantityByUserId(userId);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookOrder> findAllOpenOrders(int currentPage, int recordsPerPage) throws LibraryServiceException {
        try {
            return bookOrderDAO.findAllOpenedOrders(currentPage, recordsPerPage);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int findOpenOrdersQuantity() throws LibraryServiceException {
        try {
            return bookOrderDAO.findOpenOrdersQuantity();
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
