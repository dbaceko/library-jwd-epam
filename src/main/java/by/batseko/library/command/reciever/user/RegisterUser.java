package by.batseko.library.command.reciever.user;

import by.batseko.library.builder.user.UserBuilder;
import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUser implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User newUser = constructUser(request);

        try {
            userService.registerUser(newUser);
        } catch (LibraryServiceException e) {
            setUserInfoToSession(request, newUser);
            setErrorMessageToSession(request, e.getMessage());
            request.getSession().setAttribute(JSPAttributeStorage.PAGE, PageStorage.REGISTER_USER);
            return PageStorage.REGISTER_USER;
        }
        request.getSession().setAttribute(JSPAttributeStorage.PAGE, PageStorage.LOG_IN);
        return PageStorage.LOG_IN;
    }

    private User constructUser(HttpServletRequest request) {
        String login = request.getParameter(JSPAttributeStorage.USER_LOGIN).trim();
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        String firstName = request.getParameter(JSPAttributeStorage.USER_FIRST_NAME).trim();
        String lastName = request.getParameter(JSPAttributeStorage.USER_LAST_NAME).trim();
        String passportSerialNumber = request.getParameter(JSPAttributeStorage.USER_PASSPORT_SERIAL_NUMBER);
        String email = request.getParameter(JSPAttributeStorage.USER_EMAIL).trim().toLowerCase();
        String phoneNumber = request.getParameter(JSPAttributeStorage.USER_PHONE).trim();
        String address = request.getParameter(JSPAttributeStorage.USER_ADDRESS).trim();

        return new UserBuilder().setLogin(login)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassportSerialNumber(passportSerialNumber)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setAddress(address)
                .build();
    }

    private void setUserInfoToSession(HttpServletRequest request, User user) {
        request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
    }

    private void setErrorMessageToSession(HttpServletRequest request, String message) {
        request.setAttribute(JSPAttributeStorage.EXCEPTION_MESSAGE, message);
    }
}
