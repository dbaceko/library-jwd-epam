package by.batseko.library.command.reciever.user;

import by.batseko.library.command.*;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(JSPAttributeStorage.USER_LOGIN);
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        Router currentRouter = new Router();
        try {
            User user = userService.logIn(login, password);

            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, login);
            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getRole().toString());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());
            currentRouter.setPagePath(CommandStorage.HOME_PAGE.getCommandName());
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.LOG_IN);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }

}
