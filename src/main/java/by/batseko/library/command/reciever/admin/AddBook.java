package by.batseko.library.command.reciever.admin;

import by.batseko.library.builder.BookBuilder;
import by.batseko.library.command.*;
import by.batseko.library.command.reciever.page.AddBookPage;
import by.batseko.library.entity.book.Book;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBook implements Command {
    private static final BookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();

        Book resultBook = constructBookFromRequest(request);
        int bookQuantity = Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_QUANTITY));
        try {
            bookService.add(resultBook, bookQuantity);
        } catch (LibraryServiceException e) {
            request.setAttribute(JSPAttributeStorage.BOOK_PREVIOUS_DATA, resultBook);
            setErrorMessage(request, e.getMessage());
            return new AddBookPage().execute(request, response);
        }
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        currentRouter.setPagePath(CommandStorage.HOME_PAGE.getCommandName());
        return currentRouter;
    }

    private Book constructBookFromRequest(HttpServletRequest request) {
        return new BookBuilder().setGenreUUID(request.getParameter(JSPAttributeStorage.BOOK_GENRE))
                .setBookLanguageUUID(request.getParameter(JSPAttributeStorage.BOOK_LANGUAGE))
                .setPublisherUUID(request.getParameter(JSPAttributeStorage.BOOK_PUBLISHER))
                .setAuthorUUID(request.getParameter(JSPAttributeStorage.BOOK_AUTHOR))
                .setTitle(request.getParameter(JSPAttributeStorage.BOOK_TITLE))
                .setPublishYear(Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PUBLISH_YEAR)))
                .setPagesQuantity(Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PAGES_QUANTITY)))
                .setDescription(request.getParameter(JSPAttributeStorage.BOOK_DESCRIPTION))
                .build();
    }
}
