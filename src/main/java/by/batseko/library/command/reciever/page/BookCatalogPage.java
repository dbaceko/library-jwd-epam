package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.dto.BookDTO;
import by.batseko.library.entity.order.OrderType;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BookCatalogPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BookCatalogPage.class);

    private static final BookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_SUBSCRIPTION, OrderType.SUBSCRIPTION);
        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_READING_HOLE, OrderType.READING_HOLE);
        try {
            List<BookDTO> bookDTOList = bookService.findAllBookDTO();
            request.setAttribute(JSPAttributeStorage.BOOK_DATA_TRANSFER_OBJECT, bookDTOList);
            currentRouter.setPagePath(PageStorage.BOOK_CATALOG_PAGE);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            LOGGER.info(e.getMessage(), e);
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.HOME);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}
