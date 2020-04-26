package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookService;
import by.batseko.library.service.book.impl.CommonBookComponentsCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookPage implements Command {
    private static final BookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();

        CommonBookComponentsCache bookComponentsCache = bookService.getBookComponentsCache();
        request.setAttribute(JSPAttributeStorage.BOOK_AUTHORS, bookComponentsCache.getAuthors().getAllValues());
        request.setAttribute(JSPAttributeStorage.BOOK_GENRES, bookComponentsCache.getGenres().getAllValues());
        request.setAttribute(JSPAttributeStorage.BOOK_LANGUAGES, bookComponentsCache.getBookLanguages().getAllValues());
        request.setAttribute(JSPAttributeStorage.BOOK_PUBLISHERS, bookComponentsCache.getPublishers().getAllValues());

        currentRouter.setPagePath(PageStorage.ADD_BOOK);
        currentRouter.setRouteType(Router.RouteType.FORWARD);

        return currentRouter;
    }
}