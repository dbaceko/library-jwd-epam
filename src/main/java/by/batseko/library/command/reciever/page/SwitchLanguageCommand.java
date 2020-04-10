package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.SupportedLocaleStorage;
import by.batseko.library.exception.EnumCastException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SwitchLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String resultLang;
        try {
            String chosenLang  = request.getParameter(JSPAttributeStorage.LANGUAGE_SWITCH_PARAMETER);
            resultLang = SupportedLocaleStorage.getLocaleType(chosenLang).getLanguage();
        } catch (EnumCastException e) {
            resultLang = (String) request.getSession().getAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE);
        }
        response.addCookie(new Cookie(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang));
        request.setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang);
        return (String) request.getSession().getAttribute(JSPAttributeStorage.PAGE);
    }
}
