package by.batseko.library.command.reciever.page;

import by.batseko.library.command.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SwitchLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String resultLang;
        Router currentRouter = new Router();
        String chosenLang  = request.getParameter(JSPAttributeStorage.LANGUAGE_SWITCH_PARAMETER);
        resultLang = SupportedLocaleStorage.getLocaleFromLanguage(chosenLang).getLanguage();
        response.addCookie(new Cookie(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang));
        request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang);
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        currentRouter.setPagePath(CommandStorage.HOME_PAGE.getCommandName());
        return currentRouter;
    }
}
