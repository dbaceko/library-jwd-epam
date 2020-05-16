package by.batseko.library.command.reciever.user;

import by.batseko.library.command.*;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgetPasswordEmailSendingCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ForgetPasswordEmailSendingCommand.class);

    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String email = request.getParameter(JSPAttributeStorage.USER_EMAIL);
        try {
            userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
            String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
            currentRouter.setPagePath(redirectURL);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            LOGGER.info(e.getMessage(), e);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
            currentRouter.setPagePath(PageStorage.LOG_IN);
        }
        return currentRouter;
    }
}
