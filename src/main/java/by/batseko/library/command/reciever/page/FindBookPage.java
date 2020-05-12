package by.batseko.library.command.reciever.page;

import by.batseko.library.builder.BookBuilder;
import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.dto.BookDTO;
import by.batseko.library.entity.book.Book;
import by.batseko.library.entity.order.OrderType;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookService;
import by.batseko.library.service.book.impl.CommonBookComponentsCache;
import org.apache.commons.lang3.StringUtils;
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
        request.setAttribute(JSPAttributeStorage.BOOK_AUTHORS, bookComponentsCache.getAuthors().getAllSortedValues());
        request.setAttribute(JSPAttributeStorage.BOOK_GENRES, bookComponentsCache.getGenres().getAllSortedValues());
        request.setAttribute(JSPAttributeStorage.BOOK_LANGUAGES, bookComponentsCache.getBookLanguages().getAllSortedValues());
        request.setAttribute(JSPAttributeStorage.BOOK_PUBLISHERS, bookComponentsCache.getPublishers().getAllSortedValues());

        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_SUBSCRIPTION, OrderType.SUBSCRIPTION);
        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_READING_HOLE, OrderType.READING_HOLE);
        int currentPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_CURRENT_PAGE));
        int recordsPerPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_RECORDS_PER_PAGE));
        try {
            Book resultBook = constructBookFromRequest(request);
            LOGGER.info(resultBook);
            request.setAttribute(JSPAttributeStorage.BOOK_PREVIOUS_DATA, resultBook);
            List<BookDTO> bookDTOList = bookService.findByFields(resultBook, currentPage, recordsPerPage);
            definePaginationContext(request, bookService.findBookQuantityByFields(resultBook), currentPage, recordsPerPage);
            request.setAttribute(JSPAttributeStorage.BOOK_DATA_TRANSFER_OBJECT, bookDTOList);
            LOGGER.info(bookDTOList);
        } catch (LibraryServiceException e) {
            LOGGER.info(e.getMessage(), e);
            setErrorMessage(request, e.getMessage());
        }

        currentRouter.setPagePath(PageStorage.FIND_BOOK);
        currentRouter.setRouteType(Router.RouteType.FORWARD);

        return currentRouter;

    }

    private Book constructBookFromRequest(HttpServletRequest request) {
        return new BookBuilder().setGenreUUID(request.getParameter(JSPAttributeStorage.BOOK_GENRE))
                .setBookLanguageUUID(request.getParameter(JSPAttributeStorage.BOOK_LANGUAGE))
                .setPublisherUUID(request.getParameter(JSPAttributeStorage.BOOK_PUBLISHER))
                .setAuthorUUID(request.getParameter(JSPAttributeStorage.BOOK_AUTHOR))
                .setTitle(request.getParameter(JSPAttributeStorage.BOOK_TITLE))
                .setPublishYear(StringUtils.isBlank(request.getParameter(JSPAttributeStorage.BOOK_PUBLISH_YEAR))
                        ? 0 : Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PUBLISH_YEAR)))
                .setPagesQuantity(StringUtils.isBlank(request.getParameter(JSPAttributeStorage.BOOK_PAGES_QUANTITY))
                        ? 0 : Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PAGES_QUANTITY)))
                .build();
    }
}
