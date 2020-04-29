package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersOnlinePage implements Command {

    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND);
        request.setAttribute(JSPAttributeStorage.ONLINE_USERS_LIST, userService.getOnlineUsersCache().getAllValues());
        currentRouter.setPagePath(PageStorage.ONLINE_USERS_LIST);
        currentRouter.setRouteType(Router.RouteType.FORWARD);
        return currentRouter;
    }
}
