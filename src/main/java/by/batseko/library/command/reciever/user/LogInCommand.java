package by.batseko.library.command.reciever.user;

import by.batseko.library.command.*;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class);

    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(JSPAttributeStorage.USER_LOGIN);
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        Router currentRouter = new Router();
        try {
            User user = userService.logIn(login, password);
            LOGGER.info(user);
            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, login);
            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getUserRole().name());
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
