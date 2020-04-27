package by.batseko.library.command.reciever.page;

import by.batseko.library.command.*;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AllUsersPage implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        try {
            List<User> userList = userService.findAllUsers();
            request.setAttribute(JSPAttributeStorage.ALL_USERS_LIST, userList);
            currentRouter.setPagePath(PageStorage.ALL_USERS_LIST);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.ALL_USERS_LIST);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}
