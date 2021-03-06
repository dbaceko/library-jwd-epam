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
import by.batseko.library.entity.order.OrderStatus;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReturnBookOrder implements Command {
    private static final BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        try {
            bookOrderService.updateBookOrderStatus(constructBookOrder(request));
            String redirectCommand = request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND);
            String redirectURL = getRedirectURL(request, redirectCommand);
            currentRouter.setPagePath(redirectURL);
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
            return currentRouter;
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            return new FindBookPage().execute(request, response);
        }
    }

    private BookOrder constructBookOrder(HttpServletRequest request) {
        String orderUUID = request.getParameter(JSPAttributeStorage.ORDER_UUID);
        String orderUserLogin = request.getParameter(JSPAttributeStorage.USER_LOGIN);
        String orderUserEmail = request.getParameter(JSPAttributeStorage.USER_EMAIL);
        BookInstance bookInstance = new BookInstance();
        bookInstance.setUuid(request.getParameter(JSPAttributeStorage.BOOK_INSTANCE_UUID));
        bookInstance.setBook(new BookBuilder()
                .setTitle(request.getParameter(JSPAttributeStorage.BOOK_TITLE))
                .setAuthorName(request.getParameter(JSPAttributeStorage.BOOK_AUTHOR))
                .build());
        return new BookOrderBuilder()
                .setUuid(orderUUID)
                .setOrderStatus(OrderStatus.RETURNED)
                .setBookInstance(bookInstance)
                .setUser(new UserBuilder()
                        .setLogin(orderUserLogin)
                        .setEmail(orderUserEmail)
                        .build())
                .build();
    }
}