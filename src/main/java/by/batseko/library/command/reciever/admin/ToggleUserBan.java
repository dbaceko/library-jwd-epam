package by.batseko.library.command.reciever.admin;

import by.batseko.library.command.*;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToggleUserBan implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();

        int userId = Integer.parseInt(request.getParameter(JSPAttributeStorage.USER_ID));
        User user;
        try {
            user = userService.findUserById(userId);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.HOME);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
            return currentRouter;
        }
        try {
            user.setBanned(getReversedUserBannedStatus(user.getBanned()));
            userService.updateUserBanStatus(user);
            String redirectCommand = request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND);
            String redirectURL = getRedirectURL(request, redirectCommand);
            currentRouter.setPagePath(redirectURL);
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
        } catch (LibraryServiceException e) {
            user.setBanned(getReversedUserBannedStatus(user.getBanned()));
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.HOME);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }

    private boolean getReversedUserBannedStatus(boolean userBannedStatus) {
        return !userBannedStatus;
    }
}
