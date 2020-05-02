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
import by.batseko.library.service.book.impl.CommonBookComponentsCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindBookPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindBookPage.class);

    private static final BookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();

        CommonBookComponentsCache bookComponentsCache = bookService.getBookComponentsCache();
        request.setAttribute(JSPAttributeStorage.BOOK_AUTHORS, bookComponentsCache.getAuthors().getAllValues());
        request.setAttribute(JSPAttributeStorage.BOOK_GENRES, bookComponentsCache.getGenres().getAllValues());
        request.setAttribute(JSPAttributeStorage.BOOK_LANGUAGES, bookComponentsCache.getBookLanguages().getAllValues());
        request.setAttribute(JSPAttributeStorage.BOOK_PUBLISHERS, bookComponentsCache.getPublishers().getAllValues());

        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_SUBSCRIPTION, OrderType.SUBSCRIPTION);
        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_READING_HOLE, OrderType.READING_HOLE);
        try {
            List<BookDTO> bookDTOList = bookService.findAllBookDTO();
            request.setAttribute(JSPAttributeStorage.BOOK_DATA_TRANSFER_OBJECT, bookDTOList);
        } catch (LibraryServiceException e) {
            LOGGER.info(e.getMessage(), e);
            setErrorMessage(request, e.getMessage());
        }

        currentRouter.setPagePath(PageStorage.FIND_BOOK);
        currentRouter.setRouteType(Router.RouteType.FORWARD);

        return currentRouter;
    }
}
