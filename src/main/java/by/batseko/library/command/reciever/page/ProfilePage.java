package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfilePage implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        try {
            User user = userService.getUserByLogin(login);
            request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
            request.getSession().setAttribute(JSPAttributeStorage.PAGE, PageStorage.PROFILE_USER);
            return PageStorage.PROFILE_USER;
        } catch (LibraryServiceException e) {
            request.getSession().setAttribute(JSPAttributeStorage.PAGE, PageStorage.HOME);
            return PageStorage.HOME;
        }
    }
}
