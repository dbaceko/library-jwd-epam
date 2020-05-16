package by.batseko.library.command.reciever.admin;

import by.batseko.library.command.Command;
import by.batseko.library.command.CommandStorage;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.Router;
import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookComponentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookAuthor implements Command {
    private static final BookComponentService<Author> authorService = ServiceFactory.getInstance().getBookAuthorService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        Author author = new Author();
        author.setAuthorName(request.getParameter(JSPAttributeStorage.BOOK_AUTHOR).trim());

        try {
            authorService.add(author);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
        }
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        String redirectURL = getRedirectURL(request, CommandStorage.ADD_BOOK_COMPONENT_PAGE.getCommandName());
        currentRouter.setPagePath(redirectURL);
        return currentRouter;
    }
}
