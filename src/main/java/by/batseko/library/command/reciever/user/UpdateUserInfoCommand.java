package by.batseko.library.command.reciever.user;

import by.batseko.library.builder.user.UserBuilder;
import by.batseko.library.command.*;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserInfoCommand implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        User updatedUser = constructUser(request);
        Router currentRouter = new Router();
        try {
            userService.updateUser(updatedUser);
            updatedUser = userService.findUserById(updatedUser.getId());
            userService.getActiveUsersCache().put(updatedUser.getLogin(), updatedUser);
            currentRouter.setPagePath(CommandStorage.PROFILE_PAGE.getCommandName());
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
        } catch (LibraryServiceException e) {
            setUserInfoToRequest(request, updatedUser);
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.PROFILE_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }


    private User constructUser(HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        int id = (int) request.getSession().getAttribute(JSPAttributeStorage.USER_ID);
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        String email = request.getParameter(JSPAttributeStorage.USER_EMAIL).trim().toLowerCase();
        String phoneNumber = request.getParameter(JSPAttributeStorage.USER_PHONE).trim();
        String address = request.getParameter(JSPAttributeStorage.USER_ADDRESS).trim();

        return new UserBuilder().setLogin(login)
                .setId(id)
                .setPassword(password)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setAddress(address)
                .build();
    }

    private void setUserInfoToRequest(HttpServletRequest request, User user) {
        request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
    }
}
