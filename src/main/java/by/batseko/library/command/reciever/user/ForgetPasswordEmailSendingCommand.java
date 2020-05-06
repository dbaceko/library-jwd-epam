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
        Router router = new Router();
        String email = request.getParameter(JSPAttributeStorage.USER_EMAIL);
        try {
            userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(CommandStorage.HOME_PAGE.getCommandName());
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            LOGGER.info(e.getMessage(), e);
            router.setRouteType(Router.RouteType.FORWARD);
            router.setPagePath(PageStorage.LOG_IN);
        }
        return router;
    }
}
