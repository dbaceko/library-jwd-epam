package by.batseko.library.command.reciever.admin;

import by.batseko.library.command.Command;
import by.batseko.library.command.CommandStorage;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.Router;
import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookComponentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookPublisher  implements Command {
    private static final BookComponentService<Publisher> publisherService = ServiceFactory.getInstance().getBookPublisherService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        Publisher publisher = new Publisher();
        publisher.setPublisherTitle(request.getParameter(JSPAttributeStorage.BOOK_PUBLISHER).trim());

        try {
            publisherService.add(publisher);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
        }

        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        currentRouter.setPagePath(CommandStorage.ADD_BOOK_COMPONENT_PAGE.getCommandName());
        return currentRouter;
    }
}
