package by.batseko.library.filter;

import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.SupportedLocaleStorage;
import by.batseko.library.exception.EnumCastException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = {"/controller"})
public class LocaleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(LocaleFilter.class);

    private static final String DEFAULT_CHARSET_ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        setLocale(request, response);
        request.setCharacterEncoding(DEFAULT_CHARSET_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setLocale(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            setLocaleToSession(request, response);
        } else {
            String cookieLang = null;
            for (Cookie c : cookies) {
                if (c.getName().equals(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE)) {
                    cookieLang = c.getValue();
                }
            }
            if (cookieLang == null) {
                setLocaleToSession(request, response);
            }
        }
    }

    private void setLocaleToSession(HttpServletRequest request, HttpServletResponse response) {
        String currentLang = request.getLocale().getLanguage();
        Locale resultLocale = getLocale(currentLang);
        Cookie langCookie = new Cookie(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLocale.getLanguage());
        response.addCookie(langCookie);
    }

    private Locale getLocale(String lang) {
        try {
            SupportedLocaleStorage currentLocale = SupportedLocaleStorage.getLocaleType(lang);
            Locale resultLocale;
            switch (currentLocale) {
                case ENG:
                    resultLocale = SupportedLocaleStorage.ENG.getLocale();
                    break;
                case RUS:
                    resultLocale = SupportedLocaleStorage.RUS.getLocale();
                    break;
                default:
                    LOGGER.warn("Locale is not seted");
                    resultLocale = SupportedLocaleStorage.ENG.getLocale();
            }
            return resultLocale;
        } catch (EnumCastException e) {
            LOGGER.warn("Locale is not found", e);
            return SupportedLocaleStorage.ENG.getLocale();
        }
    }
}
