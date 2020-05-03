package by.batseko.library.command.reciever.user;

import by.batseko.library.builder.BookOrderBuilder;
import by.batseko.library.builder.UserBuilder;
import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.Router;
import by.batseko.library.command.reciever.page.FindBookPage;
import by.batseko.library.entity.book.BookInstance;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.entity.order.OrderStatus;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReturnBookOrder implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ReturnBookOrder.class);

    private static final BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        try {
            String orderUUID = request.getParameter(JSPAttributeStorage.ORDER_UUID);
            String orderUserLogin = request.getParameter(JSPAttributeStorage.USER_LOGIN);
            BookInstance bookInstance = new BookInstance();
            bookInstance.setUuid(request.getParameter(JSPAttributeStorage.BOOK_INSTANCE_UUID));
            BookOrder bookOrder = new BookOrderBuilder().setUuid(orderUUID)
                    .setOrderStatus(OrderStatus.RETURNED)
                    .setBookInstance(bookInstance)
                    .setUser(new UserBuilder().setLogin(orderUserLogin).build())
                    .build();
            bookOrderService.updateBookOrderStatus(bookOrder);
            router.setPagePath(request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND));
            router.setRouteType(Router.RouteType.REDIRECT);
            return router;
        } catch (LibraryServiceException e) {
            LOGGER.info(e.getMessage(), e);
            setErrorMessage(request, e.getMessage());
            return new FindBookPage().execute(request, response);
        }
    }
}