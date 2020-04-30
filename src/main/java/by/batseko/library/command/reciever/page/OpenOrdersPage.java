package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenOrdersPage  implements Command {
    private static final Logger LOGGER = LogManager.getLogger(OpenOrdersPage.class);

    private static final BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        try {
            List<BookOrder> bookOrders = bookOrderService.findAllOpenedRequestsOrders();
            request.setAttribute(JSPAttributeStorage.ORDER_LIST, bookOrders);
            router.setPagePath(PageStorage.OPEN_ORDERS_PAGE);
            router.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            LOGGER.info(e.getMessage(), e);
            setErrorMessage(request, e.getMessage());
            router.setPagePath(PageStorage.HOME);
            router.setRouteType(Router.RouteType.FORWARD);
        }
        return router;
    }
}