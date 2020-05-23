package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenOrdersPage  implements Command {
    private static final BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        int currentPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_CURRENT_PAGE));
        int recordsPerPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_RECORDS_PER_PAGE));
        try {
            definePaginationContext(request, bookOrderService.findOpenOrdersQuantity(), currentPage, recordsPerPage);
            List<BookOrder> bookOrders = bookOrderService.findAllOpenOrders(currentPage, recordsPerPage);
            request.setAttribute(JSPAttributeStorage.ORDER_LIST, bookOrders);
            router.setPagePath(PageStorage.OPEN_ORDERS_PAGE);
            router.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            router.setPagePath(PageStorage.HOME);
            router.setRouteType(Router.RouteType.FORWARD);
        }
        return router;
    }
}