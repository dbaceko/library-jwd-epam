package by.batseko.library.command.reciever.user;

import by.batseko.library.builder.user.UserBuilder;
import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.reciever.page.HomePage;
import by.batseko.library.command.reciever.page.ProfilePage;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserInfoCommand implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User updatedUser = constructUser(request);

        try {
            userService.updateUser(updatedUser);
            updatedUser = userService.findUserById(updatedUser.getId());
            userService.getActiveUsersCache().put(updatedUser.getLogin(), updatedUser);
        } catch (LibraryServiceException e) {
            setUserInfoToRequest(request, updatedUser);
            setErrorMessage(request, e.getMessage());
            return new ProfilePage().execute(request, response);
        }
        return new HomePage().execute(request, response);
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
