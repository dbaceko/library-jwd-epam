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

public class LogInByForgetPasswordLink implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogInByForgetPasswordLink.class);

    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String token = request.getParameter(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN);
        try {
            User user = userService.logInByToken(token);
            LOGGER.info(user);
            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, user.getLogin());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getUserRole().name());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());
            userService.deleteRememberUserToken(user.getId());
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
            String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
            currentRouter.setPagePath(redirectURL);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            LOGGER.info(e.getMessage(), e);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
            currentRouter.setPagePath(PageStorage.HOME);
        }
        return currentRouter;
    }
}