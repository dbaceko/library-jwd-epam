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

public class BookOrderServiceImpl implements BookOrderService {
    private static final Logger LOGGER = LogManager.getLogger(BookOrderServiceImpl.class);

    private static final BookOrderDAO bookOrderDAO = DAOFactory.getInstance().getBookOrderDAO();
    private static final BookInstanceDAO bookInstanceDAO = DAOFactory.getInstance().getBookInstanceDAO();

    @Override
    public void addBookOrder(BookOrder bookOrder) throws LibraryServiceException {
        List<String> availableBookInstanceUUIDs;
        try {
            availableBookInstanceUUIDs = bookInstanceDAO.findAllAvailableBookInstanceUUIDsByBoolUUID(bookOrder.getBookInstance().getUuid());
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
        LibraryServiceException availableInstancesEndedException = new LibraryServiceException("query.bookInstance.find.empty");
        for (int i = 0; i < availableBookInstanceUUIDs.size(); i++) {
            try {
                bookOrder.getBookInstance().setUuid(availableBookInstanceUUIDs.get(i));
                bookOrderDAO.addBookOrder(bookOrder);
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
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateBookOrderStatus(BookOrder bookOrder) throws LibraryServiceException {
        try {
            bookOrderDAO.updateBookOrderStatus(bookOrder);
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
}
