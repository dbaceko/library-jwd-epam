package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.PageStorage;
import by.batseko.library.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPage implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        currentRouter.setPagePath(PageStorage.REGISTER_USER);
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        return currentRouter;
    }
}
