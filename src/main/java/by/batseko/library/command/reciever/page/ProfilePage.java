package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfilePage implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        Router currentRouter = new Router();
        try {
            User user = userService.getOnlineUsersCache().get(login);
            request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
            currentRouter.setPagePath(PageStorage.PROFILE_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.PROFILE_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}
