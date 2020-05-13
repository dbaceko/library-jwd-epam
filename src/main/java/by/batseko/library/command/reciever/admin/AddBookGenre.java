package by.batseko.library.command.reciever.admin;

import by.batseko.library.command.Command;
import by.batseko.library.command.CommandStorage;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.Router;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.book.BookComponentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookGenre implements Command {
    private static final BookComponentService<Genre> genreService = ServiceFactory.getInstance().getBookGenreService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        Genre author = new Genre();
        author.setGenreTitle(request.getParameter(JSPAttributeStorage.BOOK_GENRE).trim());

        try {
            genreService.add(author);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
        }

        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        currentRouter.setPagePath(CommandStorage.ADD_BOOK_COMPONENT_PAGE.getCommandName());
        return currentRouter;
    }
}
