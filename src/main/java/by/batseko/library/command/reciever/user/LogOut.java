package by.batseko.library.command.reciever.user;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOut implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        userService.logOut(login);
        request.getSession().invalidate();
        request.getSession().setAttribute(JSPAttributeStorage.PAGE, PageStorage.HOME);
        return PageStorage.HOME;
    }
}
