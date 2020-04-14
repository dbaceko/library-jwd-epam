package by.batseko.library.command.reciever.user;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        userService.logOut(login);
        request.getSession().invalidate();
        currentRouter.setPagePath(PageStorage.HOME);
        currentRouter.setRouteType(Router.RouteType.FORWARD);
        return currentRouter;
    }
}
