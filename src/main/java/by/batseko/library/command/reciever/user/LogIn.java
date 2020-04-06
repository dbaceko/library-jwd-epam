package by.batseko.library.command.reciever.user;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogIn implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(JSPAttributeStorage.USER_LOGIN);
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        try {
            User user = userService.logIn(login, password);
            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, user.getLogin());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getRole().toString());
        } catch (LibraryServiceException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute(JSPAttributeStorage.PAGE, PageStorage.HOME);
        return PageStorage.HOME;
    }
}
