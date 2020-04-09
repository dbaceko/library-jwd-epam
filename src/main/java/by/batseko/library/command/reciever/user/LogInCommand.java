package by.batseko.library.command.reciever.user;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.reciever.page.HomePage;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(JSPAttributeStorage.USER_LOGIN);
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        try {
            User user = userService.logIn(login, password);
            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, login);
            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getRole().toString());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());
            return new HomePage().execute(request, response);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            return new LogInCommand().execute(request, response);
        }
    }
}
