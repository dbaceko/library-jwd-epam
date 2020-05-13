package by.batseko.library.command.reciever.page;

import by.batseko.library.command.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SwitchLanguageCommand implements Command {
    private static final int COOKIE_MAX_AGE_21_DAY = 60*60*24*21;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String resultLang;
        Router currentRouter = new Router();
        String chosenLang  = request.getParameter(JSPAttributeStorage.LANGUAGE_SWITCH_PARAMETER);
        resultLang = SupportedLocaleStorage.getLocaleFromLanguage(chosenLang).getLanguage();
        Cookie langCookie = new Cookie(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang);
        langCookie.setMaxAge(COOKIE_MAX_AGE_21_DAY);
        langCookie.setPath(request.getContextPath());
        response.addCookie(langCookie);
        request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang);
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        currentRouter.setPagePath(CommandStorage.HOME_PAGE.getCommandName());
        return currentRouter;
    }
}
