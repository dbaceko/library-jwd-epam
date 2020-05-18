package by.batseko.library.command.reciever.user;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostRegistrationAccountApproval implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String token = request.getParameter(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN);
        try {
            userService.postRegistrationApprovalByToken(token);
            return new LogInByTokenLink().execute(request, response);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.LOG_IN);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}