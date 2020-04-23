package by.batseko.library.command.reciever.user;

import by.batseko.library.command.*;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.user.UserService;

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
        currentRouter.setPagePath(CommandStorage.HOME_PAGE.getCommandName());
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        return currentRouter;
    }
}
