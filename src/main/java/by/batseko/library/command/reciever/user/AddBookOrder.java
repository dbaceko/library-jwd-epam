package by.batseko.library.command.reciever.user;

import by.batseko.library.builder.BookBuilder;
import by.batseko.library.builder.BookOrderBuilder;
import by.batseko.library.builder.UserBuilder;
import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.Router;
import by.batseko.library.command.reciever.page.FindBookPage;
import by.batseko.library.entity.book.BookInstance;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.entity.order.OrderType;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookOrder implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddBookOrder.class);

    private static final BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        try {
            bookOrderService.addBookOrder(constructBookOrder(request));
            router.setPagePath(request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND));
            router.setRouteType(Router.RouteType.REDIRECT);
        } catch (LibraryServiceException e) {
            LOGGER.info(e.getMessage(), e);
            setErrorMessage(request, e.getMessage());
            return new FindBookPage().execute(request, response);
        }
        return router;
    }

    private BookOrder constructBookOrder(HttpServletRequest request) {
        BookInstance bookInstance = new BookInstance();
        bookInstance.setBook(new BookBuilder().setUuid(request.getParameter(JSPAttributeStorage.BOOK_UUID)).build());
        return new BookOrderBuilder()
            .setOrderType(OrderType.valueOf(request.getParameter(JSPAttributeStorage.ORDER_TYPE_RESULT)))
            .setBookInstance(bookInstance)
            .setUser(
                new UserBuilder()
                        .setId((Integer) request.getSession().getAttribute(JSPAttributeStorage.USER_ID))
                        .setLogin((String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN))
                        .build())
            .build();
    }
}
